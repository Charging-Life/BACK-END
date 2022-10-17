package com.example.charging_life.alarm;

import com.example.charging_life.alarm.dto.*;
import com.example.charging_life.member.MemberService;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.token.TokenService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @GetMapping("/qr")
    public Object createQR(@RequestParam Long memberId, @RequestParam String chargerStatus) throws WriterException, IOException {
        int width = 200;
        int height = 200;

        String qrCodeInfo = "{" + "\n" +
                "\"id\" : " + memberId + "," + "\n" +
                "\"charger\" : \"" + chargerStatus+ "\"\n" +
                "}";

        BitMatrix matrix = new MultiFormatWriter().encode(qrCodeInfo, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());
        }
    }

}
