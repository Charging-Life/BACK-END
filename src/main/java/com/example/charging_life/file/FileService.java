package com.example.charging_life.file;

import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.file.dto.FileDto;
import com.example.charging_life.file.dto.FileResDto;
import com.example.charging_life.file.entity.File;
import com.example.charging_life.file.repository.JpaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService {
    private final JpaFileRepository fileRepository;

    @Transactional(readOnly = true)
    public FileDto findByFileId(Long id) {

        com.example.charging_life.file.entity.File entity = fileRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.FileIsNotExisted));

        FileDto fileDto = FileDto.builder()
                .fileName(entity.getFileName())
                .filePath(entity.getFilepath())
                .fileSize(entity.getFilesize())
                .build();

        return fileDto;
    }

    @Transactional(readOnly = true)
    public List<FileResDto> findAllByBoard(Long boardId) {
        List<File> fileList = fileRepository.findAllByBoardId(boardId);

        return fileList.stream()
                .map(FileResDto::new)
                .collect(Collectors.toList());
    }
}

