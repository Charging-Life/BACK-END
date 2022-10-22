package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Comment;
import com.example.charging_life.board.entity.CommentLikeMembers;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentLikeResDto {
    private Long id;
    private String comment;
    private String creationDateTime;
    private String updateDateTime;
    private Integer likes;
    private List<String> commentLikeMember = new ArrayList<>();
    private Long boardId;
    private String memberEmail;
    private String memberName;


    public CommentLikeResDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.creationDateTime = comment.getCreationDateTime();
        this.updateDateTime = comment.getUpdateDateTime();
        this.likes = comment.getLikes();
        List<CommentLikeMembers> commentLikeMember = comment.getCommentLikeMembers();
        for (CommentLikeMembers commentLikeMembers : commentLikeMember) {
            this.commentLikeMember.add(commentLikeMembers.getMember().getEmail());
        }
        this.boardId = comment.getBoard().getId();
        this.memberEmail = comment.getMember().getEmail();
        this.memberName = comment.getMember().getName();
    }
}
