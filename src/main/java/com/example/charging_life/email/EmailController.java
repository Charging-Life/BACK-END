package com.example.charging_life.email;

import com.example.charging_life.member.dto.EmailDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "이메일 인증")
    @PostMapping("/email")
    public void certifyEmail(@RequestBody EmailDto emailDto) {
        emailService.certifyEmail(emailDto);
    }

    @Operation(summary = "이메일 인증코드 확인")
    @GetMapping("/email")
    public ResponseEntity<Boolean> confirmCode(
            @RequestParam String code,
            @RequestParam String email) {
        return ResponseEntity.ok(emailService.confirmCode(code,email));
    }

    @Operation(summary = "이메일 인증코드 재발급")
    @PatchMapping("/email")
    public void reissueCode(@RequestBody EmailDto emailDto) {
        emailService.reissueCode(emailDto);
    }
}
