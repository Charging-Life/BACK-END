package com.example.charging_life.station.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ChargingStation {

    @Id @GeneratedValue
    private Long id;
    private String statNm;
    private Integer statId;
    private String address;
    private String location;
    private Double lat;
    private Double lng;
    private String useTime;
    @ManyToOne @JoinColumn(name = "business_id")
    private Business business;
    private Boolean parkingFree;
    private String note;
    private Boolean limitYn;
    private String limitDetail;

    public ChargingStation(String statNm, Integer statId, String address, String location,
                           Double lat, Double lng, String useTime, Business business, Boolean parkingFree,
                           String note, Boolean limitYn, String limitDetail) {
        this.statNm = statNm;
        this.statId = statId;
        this.address = address;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.useTime = useTime;
        this.business = business;
        this.parkingFree = parkingFree;
        this.note = note;
        this.limitYn = limitYn;
        this.limitDetail = limitDetail;
    }
}
