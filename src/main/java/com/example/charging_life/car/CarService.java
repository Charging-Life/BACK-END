package com.example.charging_life.car;

import com.example.charging_life.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final JpaCarRepo jpaCarRepo;

    public Car enrollCar(String car, Member member) {
        return jpaCarRepo.save(new Car(car, member));
    }
}
