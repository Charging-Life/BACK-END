package com.example.charging_life.api;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "공공데이터 save API" , description = "<공공 api 받아오는 API>" + "\n\n" +
        "⏰ 매주 금요일 5:00 AM 마다 새로운 충전소의 공공 api를 Business 데이터베이스에 저장 " + "\n\n" +
        "⏰ 매주 금요일 5:30 AM 마다 새로운 충전소의 공공 api를 ChargingStation & Charger 데이터베이스에 저장" + "\n\n" +
        "⏰ 5분마다 수행하여 성공하면 공공 api를 갱신하여 Charger 데이터베이스에 update")
public class ApiController {

    private final ApiService apiService;

    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 ChargingStation & Charger 데이터베이스에 저장")
    @GetMapping("/station/api")
    public void getChargingStationApi() throws IOException, ParseException {
        apiService.saveChargingStationData(false,false);
    }

    @Operation(summary = "새로운 공공 api 추가하기", description = " 매주 금요일 5:00 AM 마다 수행하여 성공하면 새로운 충전소의 공공 api를 Business 데이터베이스에 저장")
    @Scheduled(cron = "0 00 05 * 6 *")
    public void updateBusinessApi() throws IOException, ParseException {
        apiService.saveChargingStationData(true,true);
    }

    @Operation(summary = "새로운 공공 api 추가하기", description = " 매주 금요일 5:30 AM 마다 수행하여 성공하면 새로운 충전소의 공공 api를 ChargingStation & Charger 데이터베이스에 저장")
    @Scheduled(cron = "0 30 05 * 6 *")
    public void updateChargingStationApi() throws IOException, ParseException {
        apiService.saveChargingStationData(false,true);
    }

//    @Operation(summary = "5분마다 공공 api 갱신", description = " 5분마다 수행하여 성공하면 공공 api를 갱신하여 Charger 데이터베이스에 update")
//    @Scheduled(fixedDelay = 18000000)
//    public void updateCharger() throws IOException, ParseException {
//        apiService.updateChargerData();
//    }

    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 Business 데이터베이스에 저장")
    @GetMapping("/business/api")
    public void getBusinessApi() throws IOException, ParseException {
        apiService.saveChargingStationData(true,false);
    }

}
