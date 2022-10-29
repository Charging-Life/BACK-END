package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class BoardUpdateResDto {
    private Long id;
    private String title;
    private String description;
    private BoardUpdateResDto.WriterUpdateDto member;
    private Category category;
    private Optional<StationDto> chargingStation;
    private String creationDateTime;
    private String updateDateTime;

    public BoardUpdateResDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.member = new BoardUpdateResDto.WriterUpdateDto(board.getMember());
        this.category = board.getCategory();
        this.chargingStation = Optional.of(new StationDto(board.getChargingStation()));
        this.creationDateTime = board.getCreationDateTime();
        this.updateDateTime = board.getUpdateDateTime();
    }

    @Getter
    private class WriterUpdateDto {
        private Long id;
        private String email;
        private String name;
        private Auth auth;

        public WriterUpdateDto(Member member) {
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
