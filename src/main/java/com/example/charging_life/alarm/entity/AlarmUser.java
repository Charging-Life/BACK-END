package com.example.charging_life.alarm.entity;

import com.example.charging_life.board.entity.Category;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor
public class AlarmUser {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;

    @Enumerated(value = EnumType.STRING)
    private ChargerStatus chargerStatus;

    private String startCharging;

    @Builder
    public AlarmUser(Member member, ChargingStation chargingStation, ChargerStatus chargerStatus, String startCharging) {
        this.member = member;
        this.chargingStation = chargingStation;
        this.chargerStatus = chargerStatus;
        this.startCharging = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }


}
