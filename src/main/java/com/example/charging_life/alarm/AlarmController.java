package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.EnrollAlarmReqDto;
import com.example.charging_life.alarm.dto.StationStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @PostMapping("/alarm/{id}")
    public void updateStationStat(@RequestBody StationStat stationStat, @PathVariable Long id) {
        log.info(stationStat.getStatus());
        alarmService.updateStationStat(stationStat, id);
    }

    @PostMapping("/alarm")
    public Long save(@RequestBody EnrollAlarmReqDto enrollAlarmReqDto) {
        return alarmService.enrollAlarm(enrollAlarmReqDto.getStatId());
    }
}
