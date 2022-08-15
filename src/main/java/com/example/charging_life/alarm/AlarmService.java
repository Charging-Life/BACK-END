package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.StationStat;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.JpaStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final JpaAlarmRepository jpaAlarmRepository;
    private final JpaStationRepository jpaStationRepository;

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



}
