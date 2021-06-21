package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "openapi")
@Component
@Data
public class ConnectionInformation {

    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private String redirectUri;
    private String apiUrl;
}
