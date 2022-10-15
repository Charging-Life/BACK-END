package com.example.charging_life.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateReqDto {
    private String title;
    private String description;

    @Builder
    public BoardUpdateReqDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
