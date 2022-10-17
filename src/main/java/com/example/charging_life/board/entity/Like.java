package com.example.charging_life.board.entity;

import lombok.Getter;

@Getter
public enum Like {
    LIKE("LIKE"),
    UNLIKE("UNLIKE");
    private String like;
    private Like(String like){
        this.like=like;
    }
}
