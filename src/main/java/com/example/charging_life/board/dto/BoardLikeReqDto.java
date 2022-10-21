package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.LikeMembers;
import com.example.charging_life.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardLikeReqDto {
    private Board board;

    @Builder
    public BoardLikeReqDto(LikeMembers likeMembers) {;
        this.board = likeMembers.getBoard();
    }

    public LikeMembers toEntity(Member member, Board board) {
        return LikeMembers.builder()
                .member(member)
                .board(board)
                .build();
    }
}
