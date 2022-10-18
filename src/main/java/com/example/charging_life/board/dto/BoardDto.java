package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String description;
    private WriterDto member;
    private Category category;
    private Integer likes;
    private int visit;
    private List<Long> fileId;
    private String creationDateTime;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.member = new WriterDto(board.getMember());
        this.category = board.getCategory();
        this.likes = board.getLikes();
        this.visit = board.getVisit();
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
}
