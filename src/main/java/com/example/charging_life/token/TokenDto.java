package com.example.charging_life.token;

import com.example.charging_life.member.entity.Auth;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Auth auth;

    public TokenDto(String accessToken, String refreshToken, Auth auth) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.auth = auth;
    }

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}