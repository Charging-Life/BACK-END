package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChargerRepository extends JpaRepository<Charger, Long> {
    Charger findByChargerId(Integer chargerId);
    Charger findTop1ByChargingStation_IdAndChargerId(Long chargingStationId, Integer chargerId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Charger c SET c.stat = :stat WHERE c.id = :id ")
    void updateStat(Integer stat, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Charger c SET c.statUpdDt = :statUpdDt WHERE c.id = :id ")
    void updateStatUpdDt(Long statUpdDt, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Charger c SET c.lastTsdt = :lastTsdt WHERE c.id = :id ")
    void updateLastTsdt(Long lastTsdt, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Charger c SET c.lastTedt = :lastTedt WHERE c.id = :id ")
    void updateLastTedt(Long lastTedt, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Charger c SET c.nowTsdt = :nowTsdt WHERE c.id = :id ")
    void updateNowTsdt(Long nowTsdt, Long id);

}
