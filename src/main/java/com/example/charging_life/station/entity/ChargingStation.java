package com.example.charging_life.station.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ChargingStation {

    @Id @GeneratedValue
    private Long id;
    private String statNm;
    @OneToMany(mappedBy = "chargingStation")
    private List<Charger> charger = new ArrayList<>();
    private String statId;
    private String address;
    private String location;
    private Double lat;
    private Double lng;
    private String useTime;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "business_id")
    private Business business;
    private Boolean parkingFree;
    private String note;
    private Boolean limitYn;
    private String limitDetail;

    @Builder
    public ChargingStation(String statNm, List<Charger> charger, String statId,
                           String address, String location, Double lat, Double lng,
                           String useTime, Business business, Boolean parkingFree, String note,
                           Boolean limitYn, String limitDetail) {
        this.statNm = statNm;
        this.charger = charger;
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
