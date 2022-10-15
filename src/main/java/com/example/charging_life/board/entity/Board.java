package com.example.charging_life.board.entity;

import com.example.charging_life.file.entity.File;
import com.example.charging_life.member.entity.Member;
import com.example.charging_life.station.entity.ChargingStation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<File> files = new ArrayList<>();


    @Enumerated(value = EnumType.STRING)
    private Category category;

    @PrePersist
    public void prePersist() {
        this.likes = this.likes == null ? 0 : this.likes;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true) @JoinColumn(name = "chargingStation_id", nullable = true)
    private ChargingStation chargingStation;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<LikeMembers> likeMembers = new ArrayList<>();

    @ColumnDefault("0")
    private Integer likes;

    @ColumnDefault("0")
    private int visit;

    @Column(name = "creation_date_time") @CreatedDate
    private String creationDateTime;

    @Column(name = "update_date_time") @CreatedDate
    private String updateDateTime;


    @Builder
    public Board(String title, String description, Member member, List<File> files,
                 Category category, ChargingStation chargingStation, List<LikeMembers> likeMembers,
                 Integer likes, Integer visit, String creationDateTime, String updateDateTime) {
        this.title = title;
        this.description = description;
        this.member = member;
        this.files = files;
        this.category = category;
        this.chargingStation = chargingStation;
        if (chargingStation != null) {
        chargingStation.addStation(this);}
        this.likeMembers = likeMembers;
        this.likes = likes;
        this.visit = 0;
        this.creationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Board에서 파일 처리 위함
    public void addFile(File file) {
        this.files.add(file);

//        // 게시글에 파일이 저장되어있지 않은 경우
        if(file.getBoard() != this)
            // 파일 저장
            file.setBoard(this);
    }

    public void addLikes(LikeMembers likeMembers) {
        this.likeMembers.add(likeMembers);
    }
}
