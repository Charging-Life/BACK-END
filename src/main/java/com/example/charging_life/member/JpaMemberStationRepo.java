package com.example.charging_life.member;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMemberStationRepo extends JpaRepository<MemberChargingStation, Long> {
    List<MemberChargingStation> findByMember(Member member);
}

