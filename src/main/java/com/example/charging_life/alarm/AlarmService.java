package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.AlarmResDto;
import com.example.charging_life.alarm.dto.StationStat;
import com.example.charging_life.member.JpaMemberStationRepo;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.JpaStationRepository;
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
    private final JpaStationRepository jpaStationRepository;
    private final JpaMemberStationRepo jpaMemberStationRepo;

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
}
