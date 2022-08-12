package com.example.charging_life.station;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        return "success";
    }

   /* @GetMapping("/station/staus")
    public String getChargingStationApi(@RequestParam(name = "sdf") String param) throws IOException {

    }
    */
}
