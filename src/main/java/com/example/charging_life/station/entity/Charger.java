package com.example.charging_life.station.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Charger {

    @Id @GeneratedValue
    private Long Id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charging_station_id") @JsonIgnore
    private ChargingStation chargingStation;
    private Integer chargerId;
    private String chargerType;
    private Integer outPut;
    private Integer stat;
    private Integer statUpdDt;
    private Integer lastTsdt;
    private Integer lastTedt;
    private Integer nowTsdt;

    @Builder
    public Charger(ChargingStation chargingStation, Integer chargerId, String chargerType, Integer outPut,
                   Integer stat, Integer statUpdDt, Integer lastTsdt, Integer lastTedt, Integer nowTsdt) {
        this.chargingStation = chargingStation;
        this.chargerId = chargerId;
        this.chargerType = chargerType;
        this.outPut = outPut;
        this.stat = stat;
        this.statUpdDt = statUpdDt;
        this.lastTsdt = lastTsdt;
        this.lastTedt = lastTedt;
        this.nowTsdt = nowTsdt;
    }
}
