package com.example.charging_life.alarm.repository;

import com.example.charging_life.alarm.entity.Alarm;
import com.example.charging_life.station.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAlarmRepository extends JpaRepository<Alarm,Long> {

    List<Alarm> findByChargingStationOrderByIdDesc(ChargingStation chargingStation);
}
