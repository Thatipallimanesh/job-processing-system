package com.jobprocessing.job_service.controller;

import com.jobprocessing.job_service.dto.response.ApplicationResponseDto;
import com.jobprocessing.job_service.service.ApplicationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/jobs")
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkApplicationExists(@RequestParam Long jobId, @RequestParam String candidateEmail) {
        boolean exists = applicationService.checkApplicationExists(jobId, candidateEmail);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{jobId}/top-applicants")
    public ResponseEntity<List<ApplicationResponseDto>> getTopApplicants(
            @PathVariable Long jobId,
            @RequestParam @Min(value = 1, message = "Minimum value of limit is 1") @Max(value = 100, message = "Maximum value of limit is 100") int limit
    ) {
        List<ApplicationResponseDto> topApplicants = applicationService.getTopApplications(jobId, limit);
        return ResponseEntity.ok(topApplicants);
    }

    @GetMapping("/{jobId}/applicants-above-score")
    public ResponseEntity<List<ApplicationResponseDto>> getApplicantsAboveScore(
            @PathVariable Long jobId,
            @RequestParam @Min(value = 0, message = "Minimum value of minScore is 0") @Max(value = 100, message = "Maximum value of minScore is 100") int minScore
    ) {
        List<ApplicationResponseDto> applicantsAboveScore = applicationService.getApplicantsAboveScore(jobId, minScore);
        return ResponseEntity.ok(applicantsAboveScore);
    }
}
