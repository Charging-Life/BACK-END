package com.example.charging_life.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class EmailDto {
    @Email(message = "이메일 형식이 아닙니다.")
    String email;
}
