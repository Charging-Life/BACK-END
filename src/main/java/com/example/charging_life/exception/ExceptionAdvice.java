package com.example.charging_life.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExceptionAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(final CustomException e){
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        log.error(e.getMessage());
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ExceptionResponse.builder()
                        .errorCode(e.getError().getCode())
                        .description(e.getMessage())
                        .build());
    }
}
