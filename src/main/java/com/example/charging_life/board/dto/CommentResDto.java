package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentResDto {
    private Long id;
    private String comment;
    private String creationDateTime;
    private String updateDateTime;
    private Integer likes;
    private Long boardId;
    private String memberEmail;
    private String memberName;


    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.creationDateTime = comment.getCreationDateTime();
        this.updateDateTime = comment.getUpdateDateTime();
        this.likes = comment.getLikes();
        this.boardId = comment.getBoard().getId();
        this.memberEmail = comment.getMember().getEmail();
        this.memberName = comment.getMember().getName();
    }

}
