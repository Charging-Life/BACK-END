package com.example.charging_life.board.dto;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Comment;
import com.example.charging_life.board.entity.CommentLikeMembers;
import com.example.charging_life.board.entity.LikeMembers;
import com.example.charging_life.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentLikeReqDto {
    private Long memberId;
    private Long commentId;

    @Builder
    public CommentLikeReqDto(CommentLikeMembers commentLikeMembers) {;
        this.memberId = commentLikeMembers.getMember().getId();
        this.commentId = commentLikeMembers.getComment().getId();
    }

    public CommentLikeMembers toEntity(Member member, Comment comment) {
        return CommentLikeMembers.builder()
                .member(member)
                .comment(comment)
                .build();
    }
}
