package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBusinessRepository extends JpaRepository<Business, Long> {
    Business findByBusinessId(String businessId);
    List<Business> findByBusinessContaining(String business);
}
