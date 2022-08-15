package com.example.charging_life.alarm;

import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Alarm {
    @Id @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;
    private String status;
    private String statusImg;

    public Alarm(ChargingStation chargingStation) {
        this.chargingStation = chargingStation;
        this.status = "none";
    }

    public void updateStatus(String status){
        this.status = status;
    }
}
