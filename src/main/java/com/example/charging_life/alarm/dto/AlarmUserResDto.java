package com.example.charging_life.alarm.dto;

import lombok.Getter;

@Getter
public class NoticeResDto {
    private Long memberId;
    private String stationId;
    private String chargerStatus;
    private String startCharging;

    public NoticeResDto(Long memberId, String stationId, String chargerStatus, String startCharging) {
        this.memberId = memberId;
        this.stationId = stationId;
        this.chargerStatus = chargerStatus;
        this.startCharging = startCharging;
    }
}
