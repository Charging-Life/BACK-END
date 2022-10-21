package com.example.charging_life.member.repo;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberDestination;
import com.example.charging_life.station.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMemberDestinationRepo extends JpaRepository<MemberDestination,Long> {
    List<MemberDestination> findByChargingStation(ChargingStation chargingStation);
    boolean existsByIdAndMember(Long id, Member member);
    void deleteByChargingStationAndMember(ChargingStation chargingStation, Member member);
}
