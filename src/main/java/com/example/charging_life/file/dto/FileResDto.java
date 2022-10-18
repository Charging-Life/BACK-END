package com.example.charging_life.file.dto;

import com.example.charging_life.file.entity.File;
import lombok.Getter;

@Getter
public class FileResDto {
    private Long fileId;

    public FileResDto(File entity) {
        this.fileId = entity.getId();
    }
}
