package com.example.charging_life.station.repository;

import com.example.charging_life.station.entity.ChargingStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChargingStationRepository {
    private final EntityManager em;

    public List<ChargingStation> findAllByStatId(List<String> statIds) {
        String createQuery = "select c from ChargingStation c where";
        for (Integer i = 1; i < statIds.size()+1; i++) {
            if(i == 1){
                createQuery += " c.statId = " + ":" +"station" + i;
            } else {
                createQuery += " or c.statId = " + ":" + "station" + i;
            }
        }
        TypedQuery<ChargingStation> query = em.createQuery(createQuery, ChargingStation.class);
        for (Integer i = 1; i < statIds.size()+1; i++) {
            query.setParameter( "station"+i, statIds.get(i - 1));
        }
        return query.getResultList();
    }
}
