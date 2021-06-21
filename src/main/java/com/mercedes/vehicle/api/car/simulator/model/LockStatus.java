package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LockStatus implements Serializable {

    private String command;
}
