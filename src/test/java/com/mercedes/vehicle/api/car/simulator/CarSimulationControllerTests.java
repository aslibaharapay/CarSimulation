package com.mercedes.vehicle.api.car.simulator;

import com.mercedes.vehicle.api.car.simulator.controller.CarSimulationController;
import com.mercedes.vehicle.api.car.simulator.model.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CarSimulationControllerTests {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private final Vehicle vehicle;

	@Mock
	private SimpMessagingTemplate messagingTemplate;

	private String carId = "B2841247C0AA2C1E97";

	@Mock
	private ConnectionInformation connectionInformation;

	@InjectMocks
	private CarSimulationController underTest;

	CarSimulationControllerTests() {
		vehicle = new Vehicle();
		VehicleDoorStatus v = new VehicleDoorStatus();
		VehicleDoorStatusFeatures f = new VehicleDoorStatusFeatures();
		f.setValue("OPEN");
		v.setDoorLockStatusFrontLeft(f);
		vehicle.setCode("AUTH-CODE");
		vehicle.setAccessToken("ACCESS-CODE");
		vehicle.setVehicleDoorStatus(new VehicleDoorStatus());
	}

	@Test
	void Should_GetAccessTokenInformation_When_ClientConnectToApp() {

		Mockito.lenient().when(connectionInformation.getTokenUrl()).thenReturn("https://id.mercedes-benz.com/as/token.oauth2");

		AccessTokenInformation accessTokenInformation = new AccessTokenInformation();
		accessTokenInformation.setAccess_token("ACCESS_CODE");
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> multipartData = new LinkedMultiValueMap<>();
		multipartData.add("grant_type", "authorization_code");

		Mockito.lenient().when(restTemplate
				.postForObject(connectionInformation.getTokenUrl(),
						new HttpEntity<>(multipartData,headers), AccessTokenInformation.class))
				.thenReturn(accessTokenInformation);

		WSMessage<String> wsMessage = new WSMessage<>();
		wsMessage.setCode("AUTH-CODE");
		underTest.loginMercedes(wsMessage);
		assertNull(vehicle.getCode());

	}
}
