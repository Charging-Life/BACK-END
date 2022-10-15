package com.example.charging_life.alarm;

import com.example.charging_life.station.entity.Charger;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Alarm {
    @Id @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charger_id")
    private Charger charger;
    private Status status;
    private ChargerStatus chargerStatus;

    public Alarm(Charger charger) {
        this.charger = charger;
        this.status = Status.UNREAD;
        this.chargerStatus = ChargerStatus.NONE;
    }

    public void updateStatus(ChargerStatus chargerStatus) {
        this.chargerStatus = chargerStatus;
        this.status = Status.UNREAD;
    }

    public void read() {
        this.status = Status.READ;
    }
}
