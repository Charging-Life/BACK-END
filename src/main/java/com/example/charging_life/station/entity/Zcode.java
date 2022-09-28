package com.example.charging_life.station.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Zcode {

    @Id
    @GeneratedValue
    private Long Id;
    private Long zcode;
    private String city;
    @OneToMany(mappedBy = "zcode")
    private List<ChargingStation> chargingStations = new ArrayList<>();

    public Zcode(Long zcode, String city) {
        this.zcode = zcode;
        this.city = city;
    }

    public void addChargingStation(ChargingStation chargingStation) {
        this.chargingStations.add(chargingStation);
    }
}
