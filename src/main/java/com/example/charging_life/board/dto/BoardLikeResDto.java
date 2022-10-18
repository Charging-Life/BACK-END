package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import com.example.charging_life.board.entity.LikeMembers;
import com.example.charging_life.member.entity.Auth;
import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardLikeResDto {
    private Long id;
    private String title;
    private String description;
    private WriterUpdateDto member;
    private Category category;
    private List<Long> likeMember = new ArrayList<>();
    private Integer likes;
    private String creationDateTime;
    private String updateDateTime;

    public BoardLikeResDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.member = new WriterUpdateDto(board.getMember());
        this.category = board.getCategory();
        List<LikeMembers> likeMember = board.getLikeMembers();
        for (LikeMembers likeMembers : likeMember) {
            this.likeMember.add(likeMembers.getMember().getId());
        }
        this.likes = board.getLikes();
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
