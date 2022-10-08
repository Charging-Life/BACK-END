package com.example.charging_life.member.repo;

import com.example.charging_life.member.entity.MemberDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberDestinationRepo extends JpaRepository<MemberDestination,Long> {

}
