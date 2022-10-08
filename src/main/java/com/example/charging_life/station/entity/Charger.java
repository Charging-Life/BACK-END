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
    private Integer output;
    private Integer stat;
    private Long statUpdDt;
    private Long lastTsdt;
    private Long lastTedt;
    private Long nowTsdt;

    @Builder
    public Charger(ChargingStation chargingStation, Integer chargerId, String chargerType, Integer output,
                   Integer stat, Long statUpdDt, Long lastTsdt, Long lastTedt, Long nowTsdt) {
        this.chargingStation = chargingStation;
        this.chargerId = chargerId;
        this.chargerType = chargerType;
        this.output = output;
        this.stat = stat;
        this.statUpdDt = statUpdDt;
        this.lastTsdt = lastTsdt;
        this.lastTedt = lastTedt;
        this.nowTsdt = nowTsdt;
    }
}
