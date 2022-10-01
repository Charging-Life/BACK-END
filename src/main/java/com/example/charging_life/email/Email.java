package com.example.charging_life.email;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Email {
    @Id @GeneratedValue
    private Long Id;
    private String code;
    @Column(unique = true)
    private String email;
    private LocalDateTime createTime;

    public Email(String code, String email) {
        this.code = code;
        this.email = email;
        this.createTime = LocalDateTime.now();
    }

    public void updateCode(String code) {
        this.code = code;
        this.createTime = LocalDateTime.now();
    }
}
