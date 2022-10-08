package com.example.charging_life.station.dto;

import com.example.charging_life.station.entity.Charger;
import lombok.Getter;

@Getter
public class ChargerDto {
    private Integer chargerId;
    private String chargerType;
    private Integer outPut;
    private Integer stat;
    private Long statUpdDt;
    private Long lastTsdt;
    private Long lastTedt;
    private Long nowTsdt;

    public ChargerDto(Charger charger) {
        this.chargerId = charger.getChargerId();
        this.chargerType = charger.getChargerType();
        this.outPut = charger.getOutput();
        this.stat = charger.getStat();
        this.statUpdDt = charger.getStatUpdDt();
        this.lastTsdt = charger.getLastTsdt();
        this.lastTedt = charger.getLastTedt();
        this.nowTsdt = charger.getNowTsdt();
    }
}
