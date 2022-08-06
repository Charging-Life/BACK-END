package com.example.charging_life.member;

import com.example.charging_life.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepo extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
}
