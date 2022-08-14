package com.example.charging_life.member.dto;

import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberBusiness;
import com.example.charging_life.station.entity.Car;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MemberResDto {
    private String email;
    private String name;
    private Auth auth;
    private List<String> businessNames = new ArrayList<>();
    private List<String> carNames = new ArrayList<>();

    public MemberResDto(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.auth = member.getAuth();
        List<Car> cars = member.getCars();
        for (Car car : cars){
            carNames.add(car.getCar());
        }
        for (MemberBusiness memberBusiness : member.getBusinesses()) {
            businessNames.add(memberBusiness.getBusiness().getBusiness());
        }

    }
}
