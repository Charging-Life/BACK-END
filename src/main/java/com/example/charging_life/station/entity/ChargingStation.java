package com.example.charging_life.station.entity;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.car.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ChargingStation {

    @Id @GeneratedValue
    private Long id;
    private String statNm;
    @OneToMany(mappedBy = "chargingStation")
    private List<Charger> charger = new ArrayList<>();
    private String statId;
    private String address;
    private String location;
    private Double lat;
    private Double lng;
    private String useTime;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "business_id") @JsonIgnore
    private Business business;
    private Integer parkingFree;
    private String note;
    private Integer limitYn;
    private String limitDetail;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "zcode_id") @JsonIgnore
    private Zcode zcode;
    @OneToMany(mappedBy = "chargingStation") @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @Builder
    public ChargingStation(String statNm, List<Charger> charger, String statId,
                           String address, String location, Double lat, Double lng,
                           String useTime, Business business, Integer parkingFree, String note,
                           Integer limitYn, String limitDetail, Zcode zcode, List<Board> boards) {
        this.statNm = statNm;
        this.charger = charger;
        this.statId = statId;
        this.address = address;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.useTime = useTime;
        this.business = business;
        business.addChargingStation(this);
        this.parkingFree = parkingFree;
        this.note = note;
        this.limitYn = limitYn;
        this.limitDetail = limitDetail;
        this.zcode = zcode;
        zcode.addChargingStation(this);
        this.boards = boards;
    }

    public void addStation(Board board) {
        this.boards.add(board);

    }
}
