package com.example.charging_life.board.repository;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByUpdateDateTimeDesc();
    List<Board> findByCategoryOrderByUpdateDateTimeDesc(Category category);

    Optional<Board> findById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board c SET c.title = :title WHERE c.id = :id ")
    void updateTitle(String title, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board c SET c.updateDateTime = :updateDateTime WHERE c.id = :id ")
    void updateUpdateDateTime(String updateDateTime, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board c SET c.likes = :likes WHERE c.id = :id ")
    void updateLikes(Integer likes, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board c SET c.visit = :visit WHERE c.id = :id ")
    void updateVisit(int visit, Long id);
}
