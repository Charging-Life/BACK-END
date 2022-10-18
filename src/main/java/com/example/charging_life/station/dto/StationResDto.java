package com.example.charging_life.station.dto;

import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;

@Getter
public class StationResDto {
    private String statNm;
    private String statId;
    private String address;
    private String location;
    private Double lat;
    private Double lng;

    public StationResDto(ChargingStation chargingStation) {
        this.statNm = chargingStation.getStatNm();
        this.statId = chargingStation.getStatId();
        this.address = chargingStation.getAddress();
        this.location = chargingStation.getLocation();
        this.lat = chargingStation.getLat();
        this.lng = chargingStation.getLng();
    }
}
