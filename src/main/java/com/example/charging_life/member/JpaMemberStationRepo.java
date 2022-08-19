package com.example.charging_life.member;

import com.example.charging_life.member.entity.MemberChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberStationRepo extends JpaRepository<MemberChargingStation, Long> {
}

