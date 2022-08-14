package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByOperator(String operator);
}
