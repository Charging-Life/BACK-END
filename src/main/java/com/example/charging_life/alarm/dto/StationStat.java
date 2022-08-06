package com.example.charging_life.alarm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StationStat {
    private String status;

    public StationStat(String status) {
        this.status = status;
    }
}
