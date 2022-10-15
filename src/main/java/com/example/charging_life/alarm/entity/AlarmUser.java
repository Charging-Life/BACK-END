package com.example.charging_life.alarm.entity;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor
public class Notice {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;

    private String chargerStatus;

    @CreatedDate
    private String startCharging;

    public Notice(Member member, ChargingStation chargingStation, String chargerStatus) {
        this.member = member;
        this.chargingStation = chargingStation;
        this.chargerStatus = chargerStatus;
        this.startCharging = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }
}
