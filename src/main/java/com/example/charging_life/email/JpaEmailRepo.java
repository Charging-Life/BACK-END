package com.example.charging_life.email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaEmailRepo extends JpaRepository<Email,Long> {
    Optional<Email> findByEmail(String email);
}
