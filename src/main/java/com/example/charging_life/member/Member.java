package com.example.charging_life.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private boolean isAdmin;
    private Long carId;

    public Member(String email, String password, String name, String phone, boolean isAdmin, Long carId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.isAdmin = isAdmin;
        this.carId = carId;
    }
}
