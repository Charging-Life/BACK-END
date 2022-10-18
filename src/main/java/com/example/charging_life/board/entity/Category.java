package com.example.charging_life.board.entity;

import lombok.Getter;

@Getter
public enum Category {
    FREE("FREE"),
    STATION("STATION"),
    NOTICE("NOTICE");
    private String field;
    private Category(String field){
        this.field=field;
    }
}
