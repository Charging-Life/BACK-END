package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<ChargingStation, Long>{

}

