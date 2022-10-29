package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.file.entity.File;
import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String description;
    private WriterDto member;
    private Category category;
    private Integer likes;
    private Optional<StationDto> chargingStation;
    private Integer cntComments;
    private int visit;
    private List<Long> fileId = new ArrayList<>();
    private String creationDateTime;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.member = new WriterDto(board.getMember());
        this.category = board.getCategory();
        this.likes = board.getLikes();
        this.chargingStation = Optional.of(new StationDto(board.getChargingStation()));
        this.cntComments = board.getComments().size();
        this.visit = board.getVisit();
        List<File> files = board.getFiles();
        for (File file : files) {
            this.fileId.add(file.getId());
        }
        this.creationDateTime = board.getCreationDateTime();
    }

    @Getter
    private class WriterDto {
        private Long id;
        private String email;
        private String name;
        private Auth auth;

        public WriterDto(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.name = member.getName();
            this.auth = member.getAuth();
        }
    }

    @Getter
    private class StationDto {
        private Long id;
        private String statId;
        private String statNm;
        private Double lat;
        private Double lng;

        public StationDto(ChargingStation chargingStation) {
            this.id = chargingStation != null ? chargingStation.getId() : null;
            this.statId = chargingStation != null ? chargingStation.getStatId() : null;
            this.statNm = chargingStation != null ?  chargingStation.getStatNm() : null;
            this.lat = chargingStation != null ? chargingStation.getLat() : null;
            this.lng = chargingStation != null ? chargingStation.getLng() : null;
        }
    }
}
