package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.AlarmResDto;
import com.example.charging_life.alarm.dto.StationStat;
import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.repo.JpaMemberStationRepo;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.JpaChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final JpaAlarmRepository jpaAlarmRepository;
    private final JpaChargerRepository jpaChargerRepository;
    private final JpaMemberStationRepo jpaMemberStationRepo;

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

    @Transactional
    public void readAlarm(Long alarmId) {
        Alarm alarm = jpaAlarmRepository.findById(alarmId)
                .orElseThrow(()->new CustomException(ExceptionEnum.AlarmDoesNotEnroll));
        alarm.read();
    }
}
