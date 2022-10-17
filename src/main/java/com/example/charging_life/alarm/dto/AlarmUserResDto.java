package com.example.charging_life.alarm.dto;

import lombok.Getter;

@Getter
public class AlarmUserResDto {
    private Long memberId;
    private String stationId;
    private String chargerStatus;
    private String startCharging;

    public AlarmUserResDto(Long memberId, String stationId, String chargerStatus, String startCharging) {
        this.memberId = memberId;
        this.stationId = stationId;
        this.chargerStatus = chargerStatus;
        this.startCharging = startCharging;
    }
}
