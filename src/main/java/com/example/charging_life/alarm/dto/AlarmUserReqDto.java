package com.example.charging_life.alarm.dto;

import com.example.charging_life.alarm.entity.AlarmUser;
import com.example.charging_life.alarm.entity.ChargerStatus;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AlarmUserReqDto {
    private Long memberId;
    private String stationId;
    private ChargerStatus chargerStatus;

    public AlarmUserReqDto(Long memberId, String stationId, ChargerStatus chargerStatus) {
        this.memberId = memberId;
        this.stationId = stationId;
        this.chargerStatus = chargerStatus;
    }
}
