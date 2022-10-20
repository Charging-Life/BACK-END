package com.example.charging_life.board.repository;

import com.example.charging_life.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardIdOrderByUpdateDateTimeDesc(Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.comment = :comment WHERE c.id = :id ")
    void updateComment(String comment, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.updateDateTime = :updateDateTime WHERE c.id = :id ")
    void updateUpdateDateTime(String updateDateTime, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.likes = :likes WHERE c.id = :id ")
    void updateLikes(Integer likes, Long id);
}
