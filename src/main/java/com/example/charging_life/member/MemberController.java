package com.example.charging_life.member;

import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.dto.LoginReqDto;
import com.example.charging_life.member.dto.MemberReqDto;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.token.Token;
import com.example.charging_life.token.TokenDto;
import com.example.charging_life.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final TokenService tokenService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "회원 가입", description = "성공하면 memberId 반환")
    @PostMapping("/member/new")
    public void createMember(@RequestBody MemberReqDto memberRequestDto) {
        String encodePassword = passwordEncoder.encode(memberRequestDto.getPassword());
        Member member = memberRequestDto.toEntity(encodePassword);
        memberService.join(member);
        Token token = new Token(member);
        tokenService.join(token);
    }

    @Operation(summary = "로그인", description = "성공하면 access token, refresh token")
    @PostMapping("/member/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto loginReqDto) {
        Member member = memberService.findMemberByEmail(loginReqDto);
        if(passwordEncoder.matches(loginReqDto.getPassword(),member.getPassword()))
            return ResponseEntity.ok(tokenService.makeToken(member));
        else throw new CustomException(ExceptionEnum.PasswordNotMatched);
    }
}
