package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;

@Data
public class Vehicle {

    private String accessToken;
    private String code ;
    private VehicleDoorStatus vehicleDoorStatus;
}
