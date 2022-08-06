package com.example.charging_life.station.entity;

import com.example.charging_life.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Getter
@Entity
@NoArgsConstructor
public class Car {

    @Id @GeneratedValue
    private Long id;
    private String car;
    @JoinColumn(name = "member_id")
    private Member member;

    public Car(String car, Member member) {
        this.car = car;
        this.member = member;
    }
}
