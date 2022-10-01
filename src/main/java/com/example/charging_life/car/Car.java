package com.example.charging_life.car;

import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Car {

    @Id @GeneratedValue
    private Long id;
    private String car;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Car(String car, Member member) {
        this.car = car;
        this.member = member;
    }
}
