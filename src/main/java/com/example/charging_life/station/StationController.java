package com.example.charging_life.station;

import com.example.charging_life.member.MemberService;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.dto.StationResDto;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.entity.Zcode;
import com.example.charging_life.token.TokenService;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "ChargingStaion API" , description = "<충전소 및 충전기 정보 조회 API>" + "\n\n" +
        "\uD83D\uDCCC 충전소 정보와 즐겨찾기 여부 및 몇명이 접근하고있는지에 대한 정보 반환 " + "\n\n" +
        "\uD83D\uDCCC 충전소 이름(statNm) 또는 광역시,도명(city)을 이용해 충전소를 검색" + "\n\n" +
        "\uD83D\uDCCC 관리자가 등록한 충전소 기준으로 조회")
public class StationController {
    @Value("${key.serviceKey}")
    private String key;

    private final TokenService tokenService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final StationService stationService;


    @Operation(summary = "해당 statId 충전소 정보",
            description = "충전소 정보와 즐겨찾기 여부 및 몇명이 접근하고있는지에 대한 정보 반환 ")
    @GetMapping("/station/{statId}")
    public ResponseEntity<ChargingStationDto> getChargingStationId(@PathVariable String statId) throws IOException {
        return ResponseEntity.ok(stationService.findStation(statId));
    }

    @Operation(summary = "충전소 검색", description = "충전소 이름(statNm) 또는 광역시,도명(city)을 이용해 충전소를 검색할 수 있다.")
    @GetMapping("/station")
    public ResponseEntity<List<StationResDto>> getStationByQurey(@Parameter(name = "statNm", description = "?statNm=주차장")@RequestParam(value = "statNm", required = false) String statNm,
                                                                 @Parameter(name = "city", description = "?city=서울특별시")@RequestParam(value = "city", required = false) String city
                                                                  ) {
        if (statNm != null) {
            return ResponseEntity.ok(stationService.findStationByStatNm(statNm));
        }
        else {
        return ResponseEntity.ok(stationService.findStationByCity(city));
        }
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
