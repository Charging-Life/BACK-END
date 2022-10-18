package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.AlarmResDto;
import com.example.charging_life.alarm.dto.AlarmUserReqDto;
import com.example.charging_life.alarm.dto.AlarmUserResDto;
import com.example.charging_life.alarm.dto.StationStat;
import com.example.charging_life.alarm.entity.Alarm;
import com.example.charging_life.alarm.entity.AlarmUser;
import com.example.charging_life.alarm.entity.ChargerStatus;
import com.example.charging_life.alarm.repository.JpaAlarmRepository;
import com.example.charging_life.alarm.repository.JpaAlarmUserRepository;
import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.repo.JpaMemberRepo;
import com.example.charging_life.member.repo.JpaMemberStationRepo;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.JpaChargerRepository;
import com.example.charging_life.station.repository.JpaStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final JpaAlarmRepository jpaAlarmRepository;
    private final JpaAlarmUserRepository jpaAlarmUserRepository;
    private final JpaStationRepository jpaStationRepository;
    private final JpaChargerRepository jpaChargerRepository;
    private final JpaMemberStationRepo jpaMemberStationRepo;
    private final JpaMemberRepo jpaMemberRepo;

    @Transactional
    public void updateStationStat(StationStat stationStat, Long id) {
        Alarm alarm = jpaAlarmRepository.getReferenceById(id);
        alarm.updateStatus(stationStat.getStatus());
    }

    @Transactional
    public Long enrollAlarm(Integer chargerId) {
        Charger charger = jpaChargerRepository.findByChargerId(chargerId);
        Alarm alarm = jpaAlarmRepository.save(new Alarm(charger));
        return alarm.getId();
    }


    public List<AlarmResDto> getStationStat(Member member) {
        List<MemberChargingStation> stations = jpaMemberStationRepo.findByMember(member);
        List<AlarmResDto> alarmResDtos = new ArrayList<>();
        for (MemberChargingStation station : stations) {
            ChargingStation chargingStation = station.getChargingStation();
            List<Charger> chargers = chargingStation.getCharger();
            for(Charger charger : chargers) {
                Optional<Alarm> alarm = jpaAlarmRepository.findByCharger(charger);
                if(alarm.isPresent()) alarmResDtos.add(new AlarmResDto(alarm.get()));
            }
        }
        return alarmResDtos;
    }
    
    public void readAlarm(Long alarmId) {
        Alarm alarm = jpaAlarmRepository.findById(alarmId)
                .orElseThrow(()->new CustomException(ExceptionEnum.AlarmDoesNotEnroll));
        alarm.read();
    }

    @Transactional
    public AlarmUserResDto createUserAlarm(AlarmUserReqDto alarmUserReqDto) {
        System.out.println(alarmUserReqDto.getMemberId());
        Member member = jpaMemberRepo.findById(alarmUserReqDto.getMemberId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.MemberDoesNotExist));
        ChargingStation chargingStation = Optional.ofNullable(
                jpaStationRepository.findByStatId(alarmUserReqDto.getStationId()))
                .orElseThrow(() -> new CustomException(ExceptionEnum.StationDoesNotExist));
        ChargerStatus chargerStatus = alarmUserReqDto.getChargerStatus();
        AlarmUser alarmUser =  AlarmUser.builder()
                .member(member)
                .chargingStation(chargingStation)
                .chargerStatus(chargerStatus)
                .build();
        AlarmUser alarmUserInfo = jpaAlarmUserRepository.save(alarmUser);
        AlarmUserResDto alarmUserResDto = new AlarmUserResDto(alarmUserInfo);
        return alarmUserResDto;
        }

}
