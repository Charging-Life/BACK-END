package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.StationStat;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AlarmController {

    @PostMapping("/alarm/{id}")
    public void saveStationStat(@RequestBody StationStat stationStat) {
        log.info(stationStat.getStatus());
    }
}
