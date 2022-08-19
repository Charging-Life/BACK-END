package com.example.charging_life.member.entity;

import com.example.charging_life.station.entity.ChargingStation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemberChargingStation {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id") @JsonIgnore
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charging_station_id") @JsonIgnore
    private ChargingStation chargingStation;

    public MemberChargingStation(Member member, ChargingStation chargingStation) {
        this.member = member;
        this.chargingStation = chargingStation;
        member.addChargingStation(this);
    }
}
