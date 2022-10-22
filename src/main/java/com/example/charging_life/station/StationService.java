package com.example.charging_life.station;

import com.example.charging_life.api.JpaStationAnalysisRepository;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.member.entity.MemberDestination;
import com.example.charging_life.member.repo.JpaMemberDestinationRepo;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.dto.StationResDto;
import com.example.charging_life.station.entity.*;
import com.example.charging_life.station.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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


    // It is the station information function that comes out when we look for the station ID
    public ChargingStationDto findStation(String statId, Member member) {
        ChargingStationDto stationDto = makeStationDetailDto(statId);
        boolean checkDes = jpaMemberDestinationRepo.existsByChargingStation_idAndMember(stationDto.getId(), member);
        stationDto.setCheckDes(checkDes);
        return stationDto;
    }

    public ChargingStationDto findStation(String statId) {
        return makeStationDetailDto(statId);
    }


    public List<ChargingStationDto> findStationByManager(Member member) {
        List<ChargingStationDto> stationResDtos = new ArrayList<>();
        for (MemberChargingStation memberStation : member.getMemberChargingStations()) {
            ChargingStation chargingStation = memberStation.getChargingStation();
            stationResDtos.add(new ChargingStationDto(chargingStation));
        }
        return stationResDtos;
    }

    public Long fillCity(String city){
        if (city == null) {
            return null;
        } else {
            return jpaZcodeRepository.findByCity(city).getId();
        }
    }

    public Long fillBusiness(String business){
        if (business == null) {
            return null;
        } else {
            return jpaBusinessRepository.findByBusiness(business).getId();
        }
    }

    public String fillStationName(String statNm){
        if (statNm == null) {
            return null;
        } else {
            return statNm;
        }
    }

    // It is the station information function that comes out when we look for the station name or city or business name
    public List<ChargingStationDto> findStationByQuery(String statNm, String city, String business) {
        Optional<Long> cityId = Optional.ofNullable(fillCity(city));
        Optional<Long> businessId = Optional.ofNullable(fillBusiness(business));
        Optional<String> statName = Optional.ofNullable(fillStationName(statNm));
        Long check = (cityId.stream().count() + businessId.stream().count() + statName.stream().count());
        System.out.println("check"+check);
        List<ChargingStation> stationsByQuery;
        //It is the station information filtering function that comes out when we look for the station name and city and business name
        if (check >= 2) {
            if (check == 3) {stationsByQuery = jpaStationRepository.findByZcode_IdAndBusiness_IdAndStatNmContaining(cityId, businessId, statName);}
            else if (cityId == null) {stationsByQuery = jpaStationRepository.findByBusiness_IdAndStatNmContaining(businessId, statName);}
            else if (businessId == null) {stationsByQuery = jpaStationRepository.findByZcode_IdAndStatNmContaining(cityId, statName);}
            else {{stationsByQuery = jpaStationRepository.findByZcode_IdAndBusiness_Id(cityId, businessId);}
            }}
        else {
            stationsByQuery = jpaStationRepository.findByZcode_IdOrBusiness_IdOrStatNmContaining(cityId, businessId, statName);
        }
        List<ChargingStationDto> chargingStationDtoList = new ArrayList<>();
        for (ChargingStation chargingStation : stationsByQuery) {
            chargingStationDtoList.add(new ChargingStationDto(chargingStation));
        }
        return chargingStationDtoList;
    }

    public ChargingStationDto makeStationDetailDto(String statId) {
        ChargingStation chargingStation = jpaStationRepository.findByStatId(statId);
        List<MemberDestination> toMembers = jpaMemberDestinationRepo.findByChargingStation(chargingStation);
        ChargingStationDto chargingStationDto = new ChargingStationDto(chargingStation);
        chargingStationDto.addMemberCount(toMembers);
        return chargingStationDto;
    }
}
