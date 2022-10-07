package com.example.charging_life.station;

import com.example.charging_life.member.MemberService;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.dto.StationResDto;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor

@Tag(name = "ChargingStaion API" , description = "<공공 api 받아오는 API>" + "\n\n" +
        "⏰ 매주 금요일 5:00 AM 마다 새로운 충전소의 공공 api를 Business 데이터베이스에 저장 " + "\n\n" +
        "⏰ 매주 금요일 5:30 AM 마다 새로운 충전소의 공공 api를 ChargingStation & Charger 데이터베이스에 저장" + "\n\n" +
        "⏰ 5분마다 수행하여 성공하면 공공 api를 갱신하여 Charger 데이터베이스에 update")
public class StationController {
    @Value("${key.serviceKey}")
    private String key;

    private final TokenService tokenService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final StationService stationService;


    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 ChargingStation & Charger 데이터베이스에 저장")
    @GetMapping("/station/api")
    public void getChargingStationApi() throws IOException, ParseException {
        stationService.saveChargingStationData(false,false);
    }

    @Operation(summary = "새로운 공공 api 추가하기", description = " 매주 금요일 5:00 AM 마다 수행하여 성공하면 새로운 충전소의 공공 api를 Business 데이터베이스에 저장")
    @Scheduled(cron = "0 00 05 * 6 *")
    public void updateBusinessApi() throws IOException, ParseException {
        stationService.saveChargingStationData(true,true);
    }

    @Operation(summary = "새로운 공공 api 추가하기", description = " 매주 금요일 5:30 AM 마다 수행하여 성공하면 새로운 충전소의 공공 api를 ChargingStation & Charger 데이터베이스에 저장")
    @Scheduled(cron = "0 30 05 * 6 *")
    public void updateChargingStationApi() throws IOException, ParseException {
        stationService.saveChargingStationData(false,true);
    }

    @Operation(summary = "5분마다 공공 api 갱신", description = " 5분마다 수행하여 성공하면 공공 api를 갱신하여 Charger 데이터베이스에 update")
    @Scheduled(fixedDelay = 18000000)
    public void updateCharger() throws IOException, ParseException {
        stationService.updateChargerData();
    }

    @Operation(summary = "공공 api 받아오기", description = "성공하면 공공 api를 Business 데이터베이스에 저장")
    @GetMapping("/business/api")
    public void getBusinessApi() throws IOException, ParseException {
        stationService.saveChargingStationData(true,false);
    }

    @Operation(summary = "해당 statId 충전소 정보", description = "성공하면 해당 station id의 충전소 정보 받아오기 ")
    @GetMapping("/station/{statId}")
    public ResponseEntity<ChargingStationDto> getChargingStationId(@PathVariable String statId) throws IOException {
        ChargingStation chargingStation = stationService.findStation(statId);
        return ResponseEntity.ok(new ChargingStationDto(chargingStation));
    }

    @Operation(summary = "충전소 검색", description = "충전소 이름(statNm)을 이용해 충전소를 검색할 수 있다.")
    @GetMapping("/station")
    public ResponseEntity<List<StationResDto>> getStationByStatNm(@RequestParam String statNm) {
        return ResponseEntity.ok(stationService.findStationByStatNm(statNm));
    }

    @Operation(summary = "관리자 관할 충전소 조회", description = "관리자가 등록한 충전소 기준으로 조회한다.")
    @GetMapping("/station/manager")
    public ResponseEntity<List<ChargingStationDto>> getStationByManager(
            @RequestHeader(name = "Authorization") String accessToken) {
        String email = tokenService.getEmailFromToken(accessToken);
        Member member = memberService.findMemberByEmail(email);
        return ResponseEntity.ok(stationService.findStationByManager(member));
    }
}
