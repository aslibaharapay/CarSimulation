package com.mercedes.vehicle.api.car.simulator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class VehicleDoorStatus implements Serializable {

    @Value("${mercedes.carId}")
    private String carID;

    //Door Status Information
    @JsonProperty("doorstatusfrontleft")
    private VehicleDoorStatusFeatures doorStatusFrontLeft;


    @JsonProperty("doorstatusfrontright")
    private VehicleDoorStatusFeatures doorStatusFrontRight;


    @JsonProperty("doorstatusrearleft")
    private VehicleDoorStatusFeatures doorStatusRearLeft;

    @JsonProperty("doorstatusrearright")
    private VehicleDoorStatusFeatures doorStatusRearRight;

    //DoorLock Status Information

    @JsonProperty("doorlockstatusfrontleft")
    private VehicleDoorStatusFeatures doorLockStatusFrontLeft;

    @JsonProperty("doorlockstatusfrontright")
    private VehicleDoorStatusFeatures doorLockStatusFrontRight;

    @JsonProperty("doorlockstatusrearleft")
    private VehicleDoorStatusFeatures doorLockStatusRearLeft;

    @JsonProperty("doorlockstatusrearright")
    private VehicleDoorStatusFeatures doorLockStatusRearRight;

    //doorlock Status decklid Information
    @JsonProperty("doorlockstatusdecklid.")
    private VehicleDoorStatusFeatures doorLockStatusDecklid;

    //doorlock Status gas Information
    @JsonProperty("doorlockstatusgas")
    private VehicleDoorStatusFeatures doorLockStatusGas;

    //doorlock Status Vehicle Information
    @JsonProperty("doorlockstatusvehicle")
    private VehicleDoorStatusFeatures doorLockStatusVehicle;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehicleDoorStatus v = (VehicleDoorStatus) o;
        return doorStatusFrontLeft.getValue() .equals( v.doorStatusFrontLeft.getValue()) &&
                doorStatusFrontRight.getValue() .equals( v.doorStatusFrontRight.getValue()) &&
                doorStatusRearLeft.getValue() .equals( v.doorStatusRearLeft.getValue()) &&
                doorStatusRearRight.getValue() .equals( v.doorStatusRearRight.getValue()) &&
                doorLockStatusFrontLeft.getValue() .equals( v.doorLockStatusFrontLeft.getValue()) &&
                doorLockStatusFrontRight.getValue() .equals( v.doorLockStatusFrontRight.getValue()) &&
                doorLockStatusRearLeft.getValue() .equals( v.doorLockStatusRearLeft.getValue()) &&
                doorLockStatusRearRight.getValue() .equals( v.doorLockStatusRearRight.getValue());
    }
}
