package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class VehicleID implements Serializable {

    private String id;
    private String licenseplate;
    private String finorvin;
}
