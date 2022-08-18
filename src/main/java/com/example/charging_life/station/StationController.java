package com.example.charging_life.station;

import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StationController {
    @Value ("${key.serviceKey}")
    private String key;

    private final StationService stationService;


    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 ChargingStation & Charger 데이터베이스에 저장")
    @GetMapping("/station")
    public void getChargingStationApi() throws IOException {
        for (int i = 1; i < 134; i++) {
            stationService.saveChargingStationData(false,i);
        }
    }

    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 Business 데이터베이스에 저장")
    @GetMapping("/business")
    public void getBusinessApi(@RequestParam(name = "page") Integer page) throws IOException {
        stationService.saveChargingStationData(true,page);
    }

    @Operation(summary = "해당 statId 충전소 정보", description = "성공하면 해당 station id의 충전소 정보 받아오기 ")
    @GetMapping("/station/{statId}")
    public ResponseEntity<ChargingStationDto> getChargingStationId(@PathVariable String statId) throws IOException {
        ChargingStation chargingStation = stationService.findStation(statId);
        return ResponseEntity.ok(new ChargingStationDto(chargingStation));
    }
}
