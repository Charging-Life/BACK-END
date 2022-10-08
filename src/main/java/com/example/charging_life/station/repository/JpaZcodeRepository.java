package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.Zcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaZcodeRepository extends JpaRepository<Zcode, Long>{
    Zcode findByZcode(Long zcode);
    Zcode findByCity(String city);
}
