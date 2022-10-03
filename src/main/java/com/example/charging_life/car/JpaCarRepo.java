package com.example.charging_life.car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCarRepo extends JpaRepository<Car, Long> {
}
