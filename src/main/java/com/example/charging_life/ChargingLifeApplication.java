package com.example.charging_life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChargingLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargingLifeApplication.class, args);
	}

}

