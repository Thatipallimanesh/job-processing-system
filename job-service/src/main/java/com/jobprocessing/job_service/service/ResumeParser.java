package com.jobprocessing.job_service.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ResumeParser {
    public String extractText(String filePath) {
        Tika tika = new Tika();
        try {
            return tika.parseToString(new File(filePath));
        } catch (IOException | TikaException e) {
            throw new RuntimeException("Failed to parse resume");
        }
    }
}
