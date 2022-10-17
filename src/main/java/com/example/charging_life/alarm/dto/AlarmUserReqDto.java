package com.example.charging_life.alarm.dto;

import lombok.Getter;

@Getter
public class AlarmUserReqDto {
    private Long memberId;
    private String stationId;
    private String chargerStatus;

    public AlarmUserReqDto(Long memberId, String stationId, String chargerStatus) {
        this.memberId = memberId;
        this.stationId = stationId;
        this.chargerStatus = chargerStatus;
    }
}
