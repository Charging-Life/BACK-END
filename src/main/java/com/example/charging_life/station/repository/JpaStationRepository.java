package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaStationRepository extends JpaRepository<ChargingStation, Long>{
    ChargingStation findByStatId(String statId);
    List<ChargingStation> findByStatIdContaining(String statId);
    List<ChargingStation> findByStatNmContaining(String statNm);
}

