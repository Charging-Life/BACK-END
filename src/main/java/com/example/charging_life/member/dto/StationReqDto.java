package com.example.charging_life.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StationReqDto {
    private List<String> statId;
}
