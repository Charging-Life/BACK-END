package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.entity.Zcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaStationRepository extends JpaRepository<ChargingStation, Long>{
    ChargingStation findByStatId(String statId);
    List<ChargingStation> findByZcode_Id(Long zcodeId);
    List<ChargingStation> findByStatNmContaining(String statNm);
}

