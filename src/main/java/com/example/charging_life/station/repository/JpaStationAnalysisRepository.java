package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.StationAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaStationAnalysisRepository extends JpaRepository<StationAnalysis, Long> {
    StationAnalysis findByOrderByIdDesc();
}
