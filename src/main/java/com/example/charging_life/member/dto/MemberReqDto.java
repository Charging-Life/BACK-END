package com.example.charging_life.member.dto;

import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberReqDto {
    private String email;
    private String password;
    private String name;
    private String phone;

    public MemberReqDto(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public Member toEntity(String password){
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .phone(phone)
                .auth(Auth.USER)
                .build();

    }
}
