package com.example.charging_life.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReqDto {
    private String comment;
    private Long writer;

}
