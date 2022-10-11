package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.entity.Zcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaStationRepository extends JpaRepository<ChargingStation, Long>{
    ChargingStation findByStatId(String statId);
    List<ChargingStation> findByZcode_Id(Long zcodeId);
    List<ChargingStation> findByBusiness_Id(Long businessId);
    List<ChargingStation> findByStatNmContaining(String statNm);
    List<ChargingStation> findByZcode_IdOrBusiness_IdOrStatNmContaining(Optional<Long> zcodeId, Optional<Long> businessId, Optional<String> statNm);
    List<ChargingStation> findByZcode_IdAndBusiness_IdAndStatNmContaining(Optional<Long> zcodeId, Optional<Long> businessId, Optional<String> statNm);
    List<ChargingStation> findByBusiness_IdAndStatNmContaining(Optional<Long> businessId, Optional<String> statNm);
    List<ChargingStation> findByZcode_IdAndStatNmContaining(Optional<Long> zcodeId,Optional<String> statNm);
    List<ChargingStation> findByZcode_IdAndBusiness_Id(Optional<Long> zcodeId, Optional<Long> businessId);
}

