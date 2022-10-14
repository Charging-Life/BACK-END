package com.example.charging_life.station;

import com.example.charging_life.api.JpaStationAnalysisRepository;
import com.example.charging_life.api.StationAnalysis;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.member.entity.MemberDestination;
import com.example.charging_life.member.repo.JpaMemberDestinationRepo;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.dto.StationResDto;
import com.example.charging_life.station.entity.*;
import com.example.charging_life.station.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = false)
@Service
@RequiredArgsConstructor
public class StationService{

    //call the API serviceKey
    @Value("${key.serviceKey}")
    private String key;

    //Charging Station Repository
    private final JpaStationRepository jpaStationRepository;
    //Charger Repository
    private final JpaChargerRepository jpaChargerRepository;
    //Station Analysis Repository
    private final JpaStationAnalysisRepository jpaStationAnalysisRepository;
    //Business Repository
    private final JpaBusinessRepository jpaBusinessRepository;
    //Zcode Repository
    private final JpaZcodeRepository jpaZcodeRepository;
    //Member Station Repository
    private final JpaMemberDestinationRepo jpaMemberDestinationRepo;



    public ChargingStationDto findStation(String statId) {
        ChargingStation chargingStation = jpaStationRepository.findByStatId(statId);
        List<MemberDestination> toMembers = jpaMemberDestinationRepo.findByChargingStation(chargingStation);
        ChargingStationDto chargingStationDto = new ChargingStationDto(chargingStation);
        chargingStationDto.addMemberCount(toMembers);
        return chargingStationDto;
    }


    public List<StationResDto> findStationByStatNm(String statNm) {
        List<ChargingStation> stations = jpaStationRepository.findByStatNmContaining(statNm);
        List<StationResDto> stationResDtos = new ArrayList<>();
        for (ChargingStation chargingStation : stations) {
            stationResDtos.add(new StationResDto(chargingStation));
        }
        return stationResDtos;
    }

    public List<ChargingStationDto> findStationByManager(Member member) {
        List<ChargingStationDto> stationResDtos = new ArrayList<>();
        for (MemberChargingStation memberStation : member.getMemberChargingStations()) {
            ChargingStation chargingStation = memberStation.getChargingStation();
            stationResDtos.add(new ChargingStationDto(chargingStation));
        }
        return stationResDtos;
    }

    public List<StationResDto> findStationByCity(String city) {
        Long cityId = jpaZcodeRepository.findByCity(city).getId();
        List<ChargingStation> stations = jpaStationRepository.findByZcode_Id(cityId);
        List<StationResDto> stationResDtos = new ArrayList<>();
        for (ChargingStation chargingStation : stations) {
            stationResDtos.add(new StationResDto(chargingStation));
        }
        return stationResDtos;
    }

}
