package com.example.charging_life.alarm.dto;

import lombok.Getter;

@Getter
public class NoticeReqDto {
    private Long memberId;
    private String stationId;
    private String chargerStatus;

    public NoticeReqDto(Long memberId, String stationId, String chargerStatus) {
        this.memberId = memberId;
        this.stationId = stationId;
        this.chargerStatus = chargerStatus;
    }
}
