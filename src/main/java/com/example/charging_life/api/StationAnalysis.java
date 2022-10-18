package com.example.charging_life.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Entity
@NoArgsConstructor
public class StationAnalysis {

    @Id @GeneratedValue
    private Long id;
    private Integer countStatation;

    @Column(name = "creation_date_time")
    @CreatedDate
    private String creationDateTime;

    @Builder
    public StationAnalysis(Long id, Integer countStatation, String creationDateTime) {
        this.id = id;
        this.countStatation = countStatation;
        this.creationDateTime = creationDateTime;
    }
}
