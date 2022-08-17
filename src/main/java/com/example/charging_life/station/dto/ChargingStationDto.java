package com.example.charging_life.station.dto;

import com.example.charging_life.station.entity.Business;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.entity.Zcode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChargingStationDto {
    private Long id;
    private String statNm;
    private List<ChargerDto> chargerDtos = new ArrayList<>();
    private String statId;
    private String address;
    private String location;
    private Double lat;
    private Double lng;
    private String useTime;
    private String businessId;
    private String business;
    private String operator;
    private String businessCall;
    private Boolean parkingFree;
    private String note;
    private Boolean limitYn;
    private String limitDetail;
    private Long zcode;
    private String city;


    public ChargingStationDto(ChargingStation chargingStation) {
        this.id = chargingStation.getId();
        this.statNm = chargingStation.getStatNm();
        this.statId = chargingStation.getStatId();
        this.address = chargingStation.getAddress();
        this.location = chargingStation.getLocation();
        List<Charger> chargers = chargingStation.getCharger();
        for (Charger charger : chargers) {
            chargerDtos.add(new ChargerDto(charger));
        }
        this.lat = chargingStation.getLat();
        this.lng = chargingStation.getLng();
        this.useTime = chargingStation.getUseTime();
        this.businessId = chargingStation.getBusiness().getBusinessId();
        this.business = chargingStation.getBusiness().getBusiness();
        this.operator = chargingStation.getBusiness().getOperator();
        this.businessCall = chargingStation.getBusiness().getBusinessCall();
        this.parkingFree = chargingStation.getParkingFree();
        this.note = chargingStation.getNote();
        this.limitYn = chargingStation.getLimitYn();
        this.limitDetail = chargingStation.getLimitDetail();
        this.zcode = chargingStation.getZcode().getZcode();
        this.city = chargingStation.getZcode().getCity();

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
/*

@Getter
class BusinessDto{
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

@Getter
class ZcodeDto{
    private Long zcode;
    private String city;

    public ZcodeDto(Zcode zcode) {
        this.zcode = zcode.getZcode();
        this.city = zcode.getCity();
    }
}*/
