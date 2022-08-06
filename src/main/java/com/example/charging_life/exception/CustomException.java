package com.example.charging_life.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}
