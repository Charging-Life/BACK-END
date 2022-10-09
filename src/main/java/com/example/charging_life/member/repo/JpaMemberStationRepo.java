package com.example.charging_life.member.repo;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.station.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMemberStationRepo extends JpaRepository<MemberChargingStation, Long> {
    List<MemberChargingStation> findByMember(Member member);
    boolean existsByMemberAndChargingStation(Member member, ChargingStation chargingStation);
}

