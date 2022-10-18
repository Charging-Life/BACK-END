package com.example.charging_life.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaEmailRepo extends JpaRepository<Email,Long> {
    Optional<Email> findByEmail(String email);
}
