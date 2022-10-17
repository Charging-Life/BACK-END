package com.example.charging_life.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    EmailNotMatched(HttpStatus.UNAUTHORIZED,1000,"이메일을 가진 회원이 존재하지 않습니다."),
    TokenMalformed(HttpStatus.UNAUTHORIZED,1001,"지원하지 않는 형태의 토큰이 입력되었습니다."),
    SecurityInvalidToken(HttpStatus.UNAUTHORIZED,1002,"Access Token이 만료되었습니다."),
    NeedSignInAgain(HttpStatus.UNAUTHORIZED,1003,"세션이 만료되었습니다."),
    PasswordNotMatched(HttpStatus.UNAUTHORIZED,1004,"비밀번호가 일치하지 않습니다."),

    EmailDuplicated(HttpStatus.BAD_REQUEST,2000,"이미 등록된 이메일 입니다."),
    EmailNotExisted(HttpStatus.BAD_REQUEST,2001,"이메일이 존재하지 않습니다."),
    Codeexpired(HttpStatus.BAD_REQUEST,2002,"만료된 인증코드 입니다."),

    MemberDoesNotExist(HttpStatus.NOT_FOUND, 3000, "등록되지 않은 회원입니다."),
    PageDoesNotExist(HttpStatus.NOT_FOUND, 3001, "해당 게시물이 존재하지 않습니다."),
    FileDoesNotExist(HttpStatus.NOT_FOUND, 3002, "해당 파일이 존재하지 않습니다."),
    StationDoesNotExist(HttpStatus.NOT_FOUND, 3006, "해당 충전소가 존재하지 않습니다.");




    private HttpStatus status;
    private int code;
    private String description;

    private ExceptionEnum(HttpStatus status,int code, String description){
        this.code=code;
        this.status=status;
        this.description=description;
    }
}
