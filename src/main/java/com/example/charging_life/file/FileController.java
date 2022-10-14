package com.example.charging_life.file;

import com.example.charging_life.file.dto.FileDto;
import com.example.charging_life.file.repository.JpaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final JpaFileRepository jpaFileRepository;

    @CrossOrigin
    @GetMapping(
            value = "/file/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_PDF_VALUE}
    )
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws IOException {
        FileDto fileDto = fileService.findByFileId(id);
        System.out.println("fileDto" + fileDto);
        String absolutePath
                = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = fileDto.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
