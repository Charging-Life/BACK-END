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
import com.example.charging_life.station.entity.ChargingStation;
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
    private final JpaMemberStationRepo jpaMemberStationRepo;
    private final JpaMemberRepo jpaMemberRepo;

    @Transactional
    public void updateStationStat(StationStat stationStat, Long id) {
        Alarm alarm = jpaAlarmRepository.getReferenceById(id);
        alarm.updateStatus(stationStat.getStatus());
    }

    @Transactional
    public Long enrollAlarm(String statId) {
        ChargingStation chargingStation = jpaStationRepository.findByStatId(statId);
        Alarm alarm = jpaAlarmRepository.save(new Alarm(chargingStation));
        return alarm.getId();
    }


    public List<AlarmResDto> getStationStat(Member member) {
        List<MemberChargingStation> stations = jpaMemberStationRepo.findByMember(member);
        List<AlarmResDto> alarmResDtos = new ArrayList<>();
        for (MemberChargingStation station : stations) {
            ChargingStation chargingStation = station.getChargingStation();
            Optional<Alarm> alarm = jpaAlarmRepository.findByChargingStationOrderByIdDesc(chargingStation)
                    .stream().findFirst();
            if(alarm.isPresent()) alarmResDtos.add(new AlarmResDto(alarm.get().getStatus(),chargingStation));
        }
        return alarmResDtos;
    }

    @Transactional
    public AlarmUserResDto createUserAlarm(AlarmUserReqDto alarmUserReqDto) {
        System.out.println(alarmUserReqDto.getMemberId());
        Member member = jpaMemberRepo.findById(alarmUserReqDto.getMemberId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.MemberDoesNotExist));
        ChargingStation chargingStation = jpaStationRepository.findByStatId(alarmUserReqDto.getStationId());
        System.out.println(alarmUserReqDto.getChargerStatus());
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
