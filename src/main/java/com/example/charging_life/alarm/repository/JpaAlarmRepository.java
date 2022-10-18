package com.example.charging_life.alarm.repository;

import com.example.charging_life.alarm.entity.Alarm;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAlarmRepository extends JpaRepository<Alarm,Long> {

    Optional<Alarm> findByCharger(Charger charger);
}
