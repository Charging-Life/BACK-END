package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.file.entity.File;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardReqDto {
    private String title;
    private String description;
    private Category category;
    private String statId;
    private String creationDateTime;

    @Builder
    public BoardReqDto(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.category = board.getCategory();
        this.statId = board.getChargingStation().getStatId();
        this.creationDateTime = board.getCreationDateTime();
    }

    public Board toEntities(Member member, ChargingStation chargingStation) {
        return Board.builder()
                .member(member)
                .title(title)
                .description(description)
                .category(category)
                .chargingStation(chargingStation)
                .creationDateTime(creationDateTime)
                .build();
    }

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(title)
                .description(description)
                .category(category)
                .creationDateTime(creationDateTime)
                .build();
    }
}
