package com.mercedes.vehicle.api.car.simulator.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WSMessage<T> {
    private T content;
    private String code;
    private String requestId;
    private LocalDateTime timeStamp = LocalDateTime.now();

}
