package com.example.charging_life.alarm;

import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Getter
@Entity
@NoArgsConstructor
public class Alarm {
    @Id @GeneratedValue
    private Long id;
    @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;
    private String status;
    private String statusImg;
}
