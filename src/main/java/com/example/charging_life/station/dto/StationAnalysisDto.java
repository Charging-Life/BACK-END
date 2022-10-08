package com.example.charging_life.station.dto;

import com.example.charging_life.station.entity.StationAnalysis;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
public class StationAnalysisDto {
    private Integer countStatation;
    private String creationDateTime;

    public StationAnalysisDto(Integer countStatation, String creationDateTime) {
        this.countStatation = countStatation;
        this.creationDateTime = creationDateTime;
    }

}
