package com.example.charging_life.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ExceptionEnum error;
    public CustomException(ExceptionEnum e){
        super(e.getDescription());
        this.error=e;
    }
}
