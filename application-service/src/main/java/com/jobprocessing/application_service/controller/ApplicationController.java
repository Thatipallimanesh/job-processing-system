package com.jobprocessing.application_service.controller;

import com.jobprocessing.application_service.service.ApplicationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Validated
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> apply(
            @RequestParam @NotNull(message = "Job id is required") Long jobId,
            @RequestParam @NotBlank(message = "Candidate Name is required") String candidateName,
            @RequestParam @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String candidateEmail,
            @RequestParam MultipartFile resume) {
        return ResponseEntity.ok(applicationService.apply(jobId, candidateName, candidateEmail, resume));
    }
}
