package com.example.charging_life.alarm.dto;

import lombok.Getter;

@Getter
public class AlarmResDto {
    private String status;

    public AlarmResDto(String status) {
        this.status = status;
    }
}
