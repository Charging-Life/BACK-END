package com.example.charging_life.alarm.entity;

import lombok.Getter;

@Getter
public enum ChargerStatus {
    SLOW("SLOW"),
    FAST("FAST");
    private String status;
    private ChargerStatus(String status){
        this.status=status;
    }
}
