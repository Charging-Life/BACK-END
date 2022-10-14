package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateResDto {
    private Long id;
    private String title;
    private String description;
    private BoardUpdateResDto.WriterUpdateDto member;
    private Category category;
    private String creationDateTime;
    private String updateDateTime;

    public BoardUpdateResDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.member = new BoardUpdateResDto.WriterUpdateDto(board.getMember());
        this.category = board.getCategory();
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
}
