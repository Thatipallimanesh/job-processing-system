package com.jobprocessing.job_service.controller;

import com.jobprocessing.job_service.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkApplicationExists(@RequestParam Long jobId, @RequestParam String candidateEmail) {
        boolean exists = applicationService.checkApplicationExists(jobId, candidateEmail);
        return ResponseEntity.ok(exists);
    }
}
