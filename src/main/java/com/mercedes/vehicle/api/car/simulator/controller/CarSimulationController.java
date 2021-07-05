package com.mercedes.vehicle.api.car.simulator.controller;

import com.mercedes.vehicle.api.car.simulator.model.*;
import com.mercedes.vehicle.api.car.simulator.util.ConstantsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Controller
@Slf4j
public class CarSimulationController {

    @Value("${mercedes.carId}")
    private String carId;

    private final Vehicle vehicle;
    private final RestTemplate restTemplate;
    private final ConnectionInformation connectionInformation;
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public CarSimulationController(RestTemplate restTemplate, ConnectionInformation connectionInformation,
                                   SimpMessagingTemplate messagingTemplate){
        this.vehicle = new Vehicle();
        this.restTemplate = restTemplate;
        this.connectionInformation = connectionInformation;
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/login.mercedes.me")
    public void loginMercedes(@Payload WSMessage<String> payload) {
        if(vehicle.getAccessToken()==null){
            String accessToken = getAccessTokenInfo(payload.getCode());
            if(accessToken==null || vehicle.getCode()==null){
                occuredAuthError(payload.getRequestId());
                return;
            }
            vehicle.setAccessToken(accessToken);
        }
        getCarIdForUser(vehicle.getAccessToken());
        VehicleDoorStatus doorStatus = getVehicleInfo(vehicle.getAccessToken());
        getUpdatedVehicleDoorStatus(payload.getRequestId(),doorStatus);
    }

    @MessageMapping("/lock.vehicle")
    public void lockVehicle(@Payload WSMessage<String> payload) {
        if(vehicle.getAccessToken()==null){
            String accessToken = getAccessTokenInfo(payload.getCode());
            if(accessToken==null || vehicle.getCode()==null){
                occuredAuthError(payload.getRequestId());
                return;
            }
            vehicle.setAccessToken(accessToken);
        }
        updatedVehicleDoorStatus(vehicle.getAccessToken(), payload.getContent());
    }

    private void updatedVehicleDoorStatus(String accessToken,String lockStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        LockStatus l = new LockStatus();
        l.setCommand(lockStatus);

        HttpEntity<LockStatus> httpEntity = new HttpEntity<>(l,headers);
        String vehicleInfoUrl = connectionInformation.getApiUrl()+"/vehicles/"+carId+"/doors";
        try {
            ResponseEntity<String> vehicleInfoResponse = restTemplate
                    .postForEntity(vehicleInfoUrl,httpEntity, String.class);

            if (vehicleInfoResponse.getStatusCode() == HttpStatus.OK) {
                log.info("updatedVehicleDoorStatus service called -" + vehicleInfoResponse.getStatusCode());
            }
        }catch (HttpClientErrorException e){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                vehicle.setAccessToken(null);
                getAccessTokenInfo(vehicle.getCode());
                //token updated !
            }
            log.error(e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void getUpdatedVehicleDoorStatus(String reqID, VehicleDoorStatus doorStatus) {
        WSMessage<VehicleDoorStatus> wsMessage = new WSMessage<>();
        wsMessage.setRequestId(reqID);
        vehicle.setVehicleDoorStatus(doorStatus);
        wsMessage.setContent(doorStatus);
        messagingTemplate.convertAndSend("/topic/public", wsMessage);
    }

    private void occuredAuthError(String reqID){
        WSMessage<String> wsMessage = new WSMessage<>();
        wsMessage.setRequestId(reqID);
        wsMessage.setContent("ERROR");
        messagingTemplate.convertAndSend("/topic/public", wsMessage);
    }

    @Scheduled(fixedDelayString = "${mercedes.scheduleDelayInMillis}",initialDelayString = "${mercedes.initialDelayInMillis}")
    public void sendNotificationMessage() {
        if(vehicle.getCode()==null) return;

        if(vehicle.getAccessToken()==null){
            String accessToken = getAccessTokenInfo(vehicle.getCode());
            if(accessToken==null || vehicle.getCode()==null){
                occuredAuthError(ConstantsUtil.APP_BROADCAST);
                return;
            }
            vehicle.setAccessToken(accessToken);
        }
        VehicleDoorStatus doorStatus = getVehicleInfo(vehicle.getAccessToken());
        if(doorStatus!=null && !doorStatus.equals(vehicle.getVehicleDoorStatus())){
            getUpdatedVehicleDoorStatus(ConstantsUtil.APP_BROADCAST,doorStatus);
        }
    }

    private String getAccessTokenInfo(String code) {

        MultiValueMap<String, String> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("grant_type", "authorization_code");
        multipartData.add("code", code);
        multipartData.add("redirect_uri",connectionInformation.getRedirectUri());

        try {
            HttpEntity httpEntity = new HttpEntity(multipartData,createHeaders());
            ResponseEntity<AccessTokenInformation> loginResponse = restTemplate
                    .postForEntity(connectionInformation.getTokenUrl(),httpEntity, AccessTokenInformation.class);

            if (loginResponse.getStatusCode() == HttpStatus.OK) {
                vehicle.setCode(code);
                return loginResponse.getBody()!=null? loginResponse.getBody().getAccess_token():null;
            }
        }catch (HttpClientErrorException e){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                vehicle.setAccessToken(null);
                vehicle.setCode(null);
                return null;
            }
            log.error(e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        String authHeader = "Basic " +
                Base64Utils.encodeToString((connectionInformation.getClientId()
                +":"+connectionInformation.getClientSecret()).getBytes());
        headers.set( "Authorization", authHeader );
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private VehicleDoorStatus getVehicleInfo(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String vehicleInfoUrl = connectionInformation.getApiUrl()+"/vehicles/"+carId+"/doors";
        try {
            ResponseEntity<VehicleDoorStatus> vehicleInfoResponse = restTemplate
                    .exchange(vehicleInfoUrl,HttpMethod.GET,httpEntity, VehicleDoorStatus.class);

            if (vehicleInfoResponse.getStatusCode() == HttpStatus.OK) {
                return vehicleInfoResponse.getBody();
            }
        }catch (HttpClientErrorException e){
           if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
               vehicle.setAccessToken(null);
               vehicle.setVehicleDoorStatus(null);
               getAccessTokenInfo(vehicle.getCode());
                 //token updated !
            }
            log.error(e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
        }
       return null;
    }

    private void getCarIdForUser(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String vehicleInfoUrl = connectionInformation.getApiUrl()+"/vehicles";

        try {
            ResponseEntity<VehicleID[]> vehicleInfoResponse = restTemplate
                    .exchange(vehicleInfoUrl,HttpMethod.GET,httpEntity, VehicleID[].class);

            if (vehicleInfoResponse.getStatusCode() == HttpStatus.OK) {
                carId = vehicleInfoResponse.getBody().length !=0 ?
                        vehicleInfoResponse.getBody()[0].getId():carId;
                // default first car id selected
            }
        }catch (HttpClientErrorException e){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                vehicle.setAccessToken(null);
                vehicle.setVehicleDoorStatus(null);
                getAccessTokenInfo(vehicle.getCode());
                //token updated !
            }
            log.error(e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
