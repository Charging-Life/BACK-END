package com.example.charging_life.board.entity;

import com.example.charging_life.member.entity.Member;
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
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String comment;

    @Column(name = "creation_date_time") @CreatedDate
    private String creationDateTime;

    @Column(name = "update_date_time") @CreatedDate
    private String updateDateTime;


    @PrePersist
    public void prePersist() {
        this.likes = this.likes == null ? 0 : this.likes;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "comment")
    private List<CommentLikeMembers> commentLikeMembers = new ArrayList<>();

    @ColumnDefault("0")
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(String comment, String creationDateTime, String updateDateTime, List<CommentLikeMembers> commentLikeMembers,
                   Integer likes, Board board, Member member) {
        this.comment = comment;
        this.creationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.commentLikeMembers = commentLikeMembers;
        this.likes = likes;
        this.board = board;
        this.member = member;

    }
}
