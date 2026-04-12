package com.jobprocessing.application_service.controller;

import com.jobprocessing.application_service.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> apply(
            @RequestParam Long jobId,
            @RequestParam String candidateName,
            @RequestParam String candidateEmail,
            @RequestParam MultipartFile resume) {
        return ResponseEntity.ok(applicationService.apply(jobId, candidateName, candidateEmail, resume));
    }
}
