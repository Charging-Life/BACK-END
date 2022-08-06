package com.example.charging_life.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReqDto {
    private String email;
    private String password;
}
