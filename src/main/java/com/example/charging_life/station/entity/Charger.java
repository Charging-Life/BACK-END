package com.example.charging_life.station.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Getter
@Entity
@NoArgsConstructor
public class Charger {

    @Id @GeneratedValue
    private Long Id;
    @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;
    private Integer chargerId;
    private String chargerType;
    private Integer outPut;

    public Charger(ChargingStation chargingStation, Integer chargerId, String chargerType, Integer outPut) {
        this.chargingStation = chargingStation;
        this.chargerId = chargerId;
        this.chargerType = chargerType;
        this.outPut = outPut;
    }
}
