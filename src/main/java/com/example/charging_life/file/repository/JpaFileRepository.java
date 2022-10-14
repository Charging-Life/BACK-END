package com.example.charging_life.file.repository;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFileRepository extends JpaRepository<File, Long> {
    List<File> findAllByBoardId(Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE File c SET c.board = :board WHERE c.id = :id ")
    void updateboard(Board board, Long id);
}
