package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.AlarmResDto;
import com.example.charging_life.alarm.dto.EnrollAlarmReqDto;
import com.example.charging_life.alarm.dto.StationStat;
import com.example.charging_life.member.MemberService;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final TokenService tokenService;
    private final MemberService memberService;

    @PostMapping("/alarm/{id}")
    public void updateStationStat(@RequestBody StationStat stationStat, @PathVariable Long id) {
        log.info(stationStat.getStatus());
        alarmService.updateStationStat(stationStat, id);
    }

    @PostMapping("/alarm")
    public Long save(@RequestBody EnrollAlarmReqDto enrollAlarmReqDto) {
        return alarmService.enrollAlarm(enrollAlarmReqDto.getChargerId());
    }

    @GetMapping("/alarm")
    public ResponseEntity<List<AlarmResDto>> getStationStat(
            @RequestHeader(name = "Authorization") String accessToken) {
        String email = tokenService.getEmailFromToken(accessToken);
        Member member = memberService.findMemberByEmail(email);
        return ResponseEntity.ok(alarmService.getStationStat(member));
    }

    @PatchMapping("/alarm/{id}")
    public void updateAlarm(@PathVariable Long alarmId) {
        alarmService.readAlarm(alarmId);
    }
}
