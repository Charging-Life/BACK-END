package com.example.charging_life.token;

import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Token {
    @Id @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name="member_id")
    private Member member;
    private String refreshToken;

    public Token(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    public Token(Member member) {
        this.member = member;
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
