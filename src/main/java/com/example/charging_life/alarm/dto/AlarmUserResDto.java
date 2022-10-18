package com.example.charging_life.alarm.dto;

import com.example.charging_life.alarm.entity.AlarmUser;
import com.example.charging_life.alarm.entity.ChargerStatus;
import lombok.Getter;

@Getter
public class AlarmUserResDto {
    private Long memberId;
    private String stationId;
    private ChargerStatus chargerStatus;
    private String startCharging;

    public AlarmUserResDto(AlarmUser alarmUser) {
        this.memberId = alarmUser.getMember().getId();
        this.stationId = alarmUser.getChargingStation().getStatId();
        this.chargerStatus = alarmUser.getChargerStatus();
        this.startCharging = alarmUser.getStartCharging();
    }
}
