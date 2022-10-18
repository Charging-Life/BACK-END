package com.example.charging_life.token;

import com.example.charging_life.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTokenRepo extends JpaRepository<Token,Long> {
    Token findByMember(Member member);
    Token findByRefreshToken(String refreshToken);
}
