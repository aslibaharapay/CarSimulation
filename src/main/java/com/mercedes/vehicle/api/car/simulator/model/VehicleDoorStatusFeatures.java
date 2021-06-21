package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class VehicleDoorStatusFeatures implements Serializable {

    //Door Status Information
    private String value;

    private String retrievalstatus;

    private String timestamp;

}
