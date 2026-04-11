package com.jobprocessing.job_service.controller;

import com.jobprocessing.job_service.dto.request.JobRequestDto;
import com.jobprocessing.job_service.dto.response.JobResponseDto;
import com.jobprocessing.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    @PostMapping("/create")
    public ResponseEntity<JobResponseDto> createJob(@RequestBody JobRequestDto jobRequestDto) {
        return ResponseEntity.ok(jobService.createJob(jobRequestDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobResponseDto>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }
}
