package com.example.charging_life.member;

import com.example.charging_life.car.CarService;
import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.dto.*;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.token.Token;
import com.example.charging_life.token.TokenDto;
import com.example.charging_life.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final TokenService tokenService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final CarService carService;

    @Operation(summary = "회원 가입", description = "성공하면 memberId 반환")
    @PostMapping("/member/new")
    public void createMember(@RequestBody MemberReqDto memberRequestDto) {
        String encodePassword = passwordEncoder.encode(memberRequestDto.getPassword());
        Member member = memberRequestDto.toEntity(encodePassword);
        memberService.join(member);
        Token token = new Token(member);
        tokenService.join(token);
        carService.enrollCar(memberRequestDto.getCar(), member);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/member")
    public void deleteMember(@RequestHeader(name = "Authorization") String accessToken) {
        Member member = findMemberByToken(accessToken);
        memberService.drop(member);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "관리자 전용 회원 가입", description = "회사 계정을 등록할 때 사용한다.")
    @PostMapping("/member/company")
    public void createCompany(@RequestBody MemberReqDto memberRequestDto) {
        String encodePassword = passwordEncoder.encode(memberRequestDto.getPassword());
        Member member = memberRequestDto.toEntity(encodePassword);
        memberService.join(member);
        Token token = new Token(member);
        tokenService.join(token);
    }

    @Operation(summary = "로그인", description = "성공하면 access token, refresh token")
    @PostMapping("/member/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto loginReqDto) {
        Member member = memberService.findMemberByEmail(loginReqDto.getEmail());
        if(passwordEncoder.matches(loginReqDto.getPassword(),member.getPassword()))
            return ResponseEntity.ok(tokenService.makeToken(member));
        else throw new CustomException(ExceptionEnum.PasswordNotMatched);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "사용자 회원정보 조회")
    @GetMapping("/member/user")
    public ResponseEntity<MemberResDto> viewUserInfo(@RequestHeader(name = "Authorization") String accessToken) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(new MemberResDto(member));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "충전소 관리자 회원정보 조회")
    @GetMapping("/member/manager")
    public ResponseEntity<MemberResDto> viewAdminInfo(@RequestHeader(name = "Authorization") String accessToken) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(new MemberResDto(member));
    }

    @Operation(summary = "관리할 충전소 등록 또는 충전소 즐겨찾기 등록")
    @PostMapping("/member/station")
    public void enrollStation(
            @RequestHeader(name = "Authorization") String accessToken,
            @RequestBody StationReqDto stationReqDto) {
        Member member = findMemberByToken(accessToken);
        memberService.enrollStation(member, stationReqDto);
    }

    @Operation(summary = "관리할 충전소 삭제 또는 충전소 즐겨찾기 삭제")
    @DeleteMapping("/member/{statId}")
    public void deleteStation(
            @RequestHeader(name = "Authorization") String accessToken,
            @PathVariable Long statId) {
        Member member = findMemberByToken(accessToken);
        memberService.deleteStation(member, statId);
    }

    @Operation(summary = "충전소 즐겨찾기 여부 조회")
    @GetMapping("member/station/{statId}")
    public ResponseEntity<Boolean> checkFavorite(
            @RequestHeader(name = "Authorization") String accessToken,
            @PathVariable String statId) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(memberService.checkFavorite(member,statId));
    }

    @PreAuthorize("hasAuthority('COMPANY')")
    @Operation(summary = "기업 회원정보 조회")
    @GetMapping("/member/company")
    public ResponseEntity<MemberResDto> viewCompanyInfo(@RequestHeader(name = "Authorization") String accessToken) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(new MemberResDto(member));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "충전소 목적지 등록")
    @PostMapping("/member/destination")
    public void enrollDestination(
            @RequestHeader(name = "Authorization") String accessToken,
            @RequestBody StationReqDto stationReqDto) {

        Member member = findMemberByToken(accessToken);
        memberService.enrollDestination(member,stationReqDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "충전소 목적지 취소")
    @DeleteMapping("/member/destination/{statId}")
    public void removeDestination(
            @RequestHeader(name = "Authorization") String accessToken,
            @PathVariable String statId) {

        Member member = findMemberByToken(accessToken);
        memberService.removeDestination(member,statId);
    }

    public Member findMemberByToken(String accessToken) {
        String email = tokenService.getEmailFromToken(accessToken);
        Member member = memberService.findMemberByEmail(email);
        return member;
    }
}
