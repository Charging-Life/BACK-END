package com.example.charging_life.alarm.repository;

import com.example.charging_life.alarm.entity.AlarmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAlarmUserRepository extends JpaRepository<AlarmUser, Long> {
}
