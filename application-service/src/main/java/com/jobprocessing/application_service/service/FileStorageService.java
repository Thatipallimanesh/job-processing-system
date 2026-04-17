package com.jobprocessing.application_service.service;

import com.jobprocessing.common_lib.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    public String saveResume(MultipartFile file) {
        try {
            String uploadDir = "uploads/resumes/";
            File dir  = new File(uploadDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        } catch (IOException e) {
            throw new FileStorageException("File upload failed");
        }
    }
}
