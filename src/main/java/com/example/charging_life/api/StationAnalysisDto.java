package com.example.charging_life.api;

import lombok.Getter;

@Getter
public class StationAnalysisDto {
    private Integer countStatation;
    private String creationDateTime;

    public StationAnalysisDto(Integer countStatation, String creationDateTime) {
        this.countStatation = countStatation;
        this.creationDateTime = creationDateTime;
    }

}
