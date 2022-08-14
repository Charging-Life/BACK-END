package com.example.charging_life.station;

import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.entity.ChargingStation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class StationController {
    @Value ("${key.serviceKey}")
    private String key;

    private final StationService stationService;


    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 데이터베이스에 저장")
    @GetMapping("/station")
    public String getChargingStationApi() throws IOException {
        stationService.saveChargingStationData();
        return stationService.toString();
    }

    @GetMapping("/station/{id}")
    public ResponseEntity<ChargingStationDto> getChargingStationId(@PathVariable Long id) throws IOException {
        ChargingStation chargingStation = stationService.findStation(id);
        return ResponseEntity.ok(new ChargingStationDto(chargingStation));
    }

   @GetMapping("/station/statId")
    public String getChargingStationStatId(@RequestParam(name = "statId") String statId) throws IOException {
       return "success";
    }

}
