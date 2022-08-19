package com.example.charging_life.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAlarmRepository extends JpaRepository<Alarm,Long> {

}
