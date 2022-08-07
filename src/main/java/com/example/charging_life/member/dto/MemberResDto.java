package com.example.charging_life.member.dto;

import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.Car;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResDto {
    private String email;
    private String name;
    private Auth auth;
    private List<Car> cars;

    public MemberResDto(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.auth = member.getAuth();
        this.cars = member.getCars();
    }
}
