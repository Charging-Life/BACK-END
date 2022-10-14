package com.example.charging_life.api;

import com.example.charging_life.api.StationAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStationAnalysisRepository extends JpaRepository<StationAnalysis, Long> {
    StationAnalysis findByOrderByIdDesc();
}
