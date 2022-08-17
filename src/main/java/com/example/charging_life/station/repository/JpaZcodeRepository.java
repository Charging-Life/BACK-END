package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.Zcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaZcodeRepository extends JpaRepository<Zcode, Long>{
    Zcode findByZcode(Long zcode);
}
