package com.example.charging_life.board.repository;

import com.example.charging_life.board.entity.Board;
import com.example.charging_life.board.entity.LikeMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLikeMembersRepository extends JpaRepository<LikeMembers, Long> {
    List<LikeMembers> findByBoard_id(Long id);
    LikeMembers findByBoard_idAndMember_id(Long boardId, Long Memberid);
}
