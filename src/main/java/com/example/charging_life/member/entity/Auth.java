package com.example.charging_life.member.entity;

import lombok.Getter;

@Getter
public enum Auth {
    ADMIN("ADMIN"),
    USER("MEMBER");
    private String role;
    private Auth(String role){
        this.role=role;
    }
}
