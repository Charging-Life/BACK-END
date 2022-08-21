package com.example.charging_life.alarm.dto;

import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;

@Getter
public class AlarmResDto {
    private String status;
    private String statId;
    private String address;
    private String statNm;

    public AlarmResDto(String status, ChargingStation chargingStation) {
        this.status = status;
        this.statId = chargingStation.getStatId();
        this.address = chargingStation.getAddress();
        this.statNm = chargingStation.getStatNm();
    }
}
