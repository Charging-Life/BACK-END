package com.example.charging_life.alarm.dto;

import com.example.charging_life.alarm.ChargerStatus;
import com.example.charging_life.alarm.Status;
import com.example.charging_life.alarm.entity.Alarm;
import lombok.Getter;

@Getter
public class AlarmResDto {
    private Long alarmId;
    private ChargerStatus chargerStatus;
    private String statId;
    private String address;
    private String statNm;
    private Status status;

    public AlarmResDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.chargerStatus = alarm.getChargerStatus();
        this.status = alarm.getStatus();
        this.statId = alarm.getCharger().getChargingStation().getStatId();
        this.address = alarm.getCharger().getChargingStation().getAddress();
        this.statNm = alarm.getCharger().getChargingStation().getStatNm();
    }
}
