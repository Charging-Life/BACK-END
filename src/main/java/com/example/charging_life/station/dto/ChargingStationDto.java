package com.example.charging_life.station.dto;

import com.example.charging_life.station.entity.Business;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChargingStationDto {
    private Long id;
    private String statNm;
    private List<ChargerDto> chargers = new ArrayList<>();
    private String statId;
    private String address;
    private String location;
    private Double lat;
    private Double lng;
    private String useTime;
    private BusinessDto business;
    private Boolean parkingFree;
    private String note;
    private Boolean limitYn;
    private String limitDetail;

    public ChargingStationDto(ChargingStation chargingStation) {
        this.id = chargingStation.getId();
        this.statNm = chargingStation.getStatNm();
        this.statId = chargingStation.getStatId();
        this.address = chargingStation.getAddress();
        this.location = chargingStation.getLocation();
        List<Charger> chargers = chargingStation.getCharger();
        for (Charger charger : chargers) {
            this.chargers.add(new ChargerDto(charger));
        }
        this.lat = chargingStation.getLat();
        this.lng = chargingStation.getLng();
        this.useTime = chargingStation.getUseTime();
        this.business = new BusinessDto(chargingStation.getBusiness());
        this.parkingFree = chargingStation.getParkingFree();
        this.note = chargingStation.getNote();
        this.limitYn = chargingStation.getLimitYn();
        this.limitDetail = chargingStation.getLimitDetail();
    }
}

@Getter
class ChargerDto {
    private Integer chargerId;
    private String chargerType;
    private Integer outPut;

    public ChargerDto(Charger charger) {
        this.chargerId = charger.getChargerId();;
        this.chargerType = charger.getChargerType();
        this.outPut =charger.getOutPut();
    }
}

@Getter
class BusinessDto {
    private String businessId;
    private String business;
    private String operator;
    private String businessCall;

    public BusinessDto(Business business) {
        this.businessId = business.getBusinessId();
        this.business = business.getBusiness();
        this.operator = business.getOperator();
        this.businessCall = business.getBusinessCall();
    }
}
