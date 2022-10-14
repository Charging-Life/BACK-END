package com.example.charging_life.file.entity;

import com.example.charging_life.board.entity.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class File {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String fileName;

    private String filepath;

    private Long filesize;

    @Builder
    public File(String fileName, String filepath, Long filesize) {
        this.fileName = fileName;
        this.filepath = filepath;
        this.filesize = filesize;
    }


    public void setBoard(Board board){
        this.board = board;


        if(!board.getFiles().contains(this))
            board.getFiles().add(this);
    }

}
