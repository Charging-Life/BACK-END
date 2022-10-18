package com.example.charging_life.alarm.dto;

import com.example.charging_life.alarm.ChargerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StationStat {
    private ChargerStatus status;

    public StationStat(ChargerStatus status) {
        this.status = status;
    }
}
