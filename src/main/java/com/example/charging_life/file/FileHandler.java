package com.example.charging_life.file;

import com.example.charging_life.file.dto.FileDto;
import com.example.charging_life.file.entity.File;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    private final FileService fileService;

    public FileHandler(FileService fileService) {
        this.fileService = fileService;
    }

    public List<File> parseFileInfo(
            List<MultipartFile> multipartFiles
    )throws Exception {

        List<File> fileList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(multipartFiles)) {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);


            String absolutePath = new java.io.File("").getAbsolutePath() + java.io.File.separator + java.io.File.separator;


            String path = "file" + java.io.File.separator + current_date;
            java.io.File file = new java.io.File(path);


            if(!file.exists()) {
                boolean wasSuccessful = file.mkdirs();


                if(!wasSuccessful)
                    System.out.println("file: was not successful");
            }


            for(MultipartFile multipartFile : multipartFiles) {


                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/jpg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else if(contentType.contains("application/pdf"))
                        originalFileExtension = ".pdf";
                    else
                        break;
                }


                String new_file_name = System.nanoTime() + originalFileExtension;


                FileDto fileDto = FileDto.builder()
                        .fileName(multipartFile.getOriginalFilename())
                        .filePath(path + java.io.File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();


                File file1 = new File(
                        fileDto.getFileName(),
                        fileDto.getFilePath(),
                        fileDto.getFileSize()
                );

                fileList.add(file1);

                file = new java.io.File(absolutePath + path + java.io.File.separator + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return fileList;
    }
}