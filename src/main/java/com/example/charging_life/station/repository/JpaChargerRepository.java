package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChargerRepository extends JpaRepository<Charger, Long> {
}
