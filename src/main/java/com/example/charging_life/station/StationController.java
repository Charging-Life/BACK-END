package com.example.charging_life.station;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/station")
    public String getChargingStationApi() throws IOException {
        stationService.saveChargingStationData();
        return "success";
    }
}
