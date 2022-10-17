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
    private List<Long> commentLikeMember = new ArrayList<>();
    private Long boardId;
    private Long memberId;


    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.creationDateTime = comment.getCreationDateTime();
        this.updateDateTime = comment.getUpdateDateTime();
        this.likes = comment.getLikes();
        List<CommentLikeMembers> commentLikeMember = comment.getCommentLikeMembers();
        for (CommentLikeMembers commentLikeMembers : commentLikeMember) {
            this.commentLikeMember.add(commentLikeMembers.getMember().getId());
        }
        this.boardId = comment.getBoard().getId();
        this.memberId = comment.getMember().getId();
    }

    @Getter
    private class MemberLikeDto {
        private Long id;

        public MemberLikeDto(Member member) {
            this.id = member.getId();
        }
    }
}
