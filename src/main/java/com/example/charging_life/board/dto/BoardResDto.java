package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class BoardResDto {
    private Long id;
    private String title;
    private String description;
    private WriterDto member;
    private Category category;
    private Optional<StationDto> chargingStation;
    private Integer likes;
    private int visit;
    private List<Long> fileId;
    private String creationDateTime;

    public BoardResDto(Board board, List<Long> fileId) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.member = new WriterDto(board.getMember());
        this.category = board.getCategory();
        this.chargingStation = Optional.of(new StationDto(board.getChargingStation()));
        this.likes = board.getLikes();
        this.visit = board.getVisit();
        this.fileId = fileId;
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

        public StationDto(ChargingStation chargingStation) {
            this.id = chargingStation != null ? chargingStation.getId() : null;
            this.statId = chargingStation != null ? chargingStation.getStatId() : null;
            this.statNm = chargingStation != null ?  chargingStation.getStatNm() : null;
        }
    }
}
