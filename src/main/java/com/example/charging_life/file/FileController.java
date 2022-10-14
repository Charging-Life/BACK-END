package com.example.charging_life.file;

import com.example.charging_life.file.dto.FileDto;
import com.example.charging_life.file.repository.JpaFileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "File API" , description = "<다중 파일 API>" + "\n\n" +
        "\uD83D\uDCCC jpeg " +  "\n\n" +
        "\uD83D\uDCCC png " +  "\n\n" +
        "\uD83D\uDCCC pdf " )
public class FileController {
    private final FileService fileService;
    private final JpaFileRepository jpaFileRepository;

    @CrossOrigin
    @Operation(summary = "파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 파일 출력")
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
