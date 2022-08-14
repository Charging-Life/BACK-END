package com.example.charging_life.station.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor
public class Charger {

    @Id @GeneratedValue
    private Long Id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;
    private Integer chargerId;
    private String chargerType;
    private Integer outPut;

    @Builder
    public Charger(ChargingStation chargingStation, Integer chargerId, String chargerType, Integer outPut) {
        this.chargingStation = chargingStation;
        this.chargerId = chargerId;
        this.chargerType = chargerType;
        this.outPut = outPut;
    }
}
