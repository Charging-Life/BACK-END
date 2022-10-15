package com.example.charging_life.station.dto;

import com.example.charging_life.member.entity.MemberDestination;
import com.example.charging_life.station.entity.Business;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.entity.*;
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
    private Integer parkingFree;
    private String note;
    private Integer limitYn;
    private String limitDetail;
    private Long zcode;
    private String city;
    private Integer memberCount;


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
        this.zcode = chargingStation.getZcode().getZcode();
        this.city = chargingStation.getZcode().getCity();

    }

    public void addMemberCount(List<MemberDestination> toMembers) {
        this.memberCount = toMembers.size();
    }

    @Getter
    private class ChargerDto {
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

    @Getter
    private class BusinessDto {
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



}

