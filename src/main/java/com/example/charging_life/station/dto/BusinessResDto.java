package com.example.charging_life.station.dto;

import com.example.charging_life.station.entity.Business;
import lombok.Getter;

@Getter
public class BusinessDto {
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
