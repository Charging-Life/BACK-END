package com.example.charging_life.board.repository;

import com.example.charging_life.board.entity.CommentLikeMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCommentLikeMembersRepository  extends JpaRepository<CommentLikeMembers, Long> {
    List<CommentLikeMembers> findByComment_id(Long id);
    Optional<CommentLikeMembers> findByComment_idAndMember_id(Long commentId, Long memberid);
}
