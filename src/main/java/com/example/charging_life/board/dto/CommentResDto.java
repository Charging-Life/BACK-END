package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Comment;
import com.example.charging_life.board.entity.CommentLikeMembers;
import com.example.charging_life.board.entity.LikeMembers;
import com.example.charging_life.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class CommentResDto {
    private Long id;
    private String comment;
    private String creationDateTime;
    private String updateDateTime;
    private Integer likes;
    private Long boardId;
    private Long memberId;


    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.creationDateTime = comment.getCreationDateTime();
        this.updateDateTime = comment.getUpdateDateTime();
        this.likes = comment.getLikes();
        this.boardId = comment.getBoard().getId();
        this.memberId = comment.getMember().getId();
    }

}
