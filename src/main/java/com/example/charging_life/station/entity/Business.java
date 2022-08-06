package com.example.charging_life.station.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Business {

    @Id @GeneratedValue
    private Long id;
    private String businessId;
    private String business;
    private String businessCall;
    private String businessImg;

    public Business(String businessId, String business, String businessCall, String businessImg) {
        this.businessId = businessId;
        this.business = business;
        this.businessCall = businessCall;
        this.businessImg = businessImg;
    }
}
