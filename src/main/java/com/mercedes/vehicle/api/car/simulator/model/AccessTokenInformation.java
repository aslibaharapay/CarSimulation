package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AccessTokenInformation implements Serializable {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;

}
