package com.jobprocessing.job_service.service;

import com.jobprocessing.common_lib.exception.ResourceNotFoundException;
import com.jobprocessing.job_service.dto.request.JobRequestDto;
import com.jobprocessing.job_service.dto.response.JobResponseDto;
import com.jobprocessing.job_service.entity.Job;
import com.jobprocessing.job_service.repository.JobRepository;
import com.jobprocessing.job_service.utils.JobMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponseDto createJob(JobRequestDto jobRequestDto) {
        Job job = JobMapper.toEntity(jobRequestDto);
        job.setCreatedAt(LocalDateTime.now());
        Job savedJob = jobRepository.save(job);
        return JobMapper.toDto(savedJob);
    }

    public List<JobResponseDto> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(JobMapper::toDto)
                .toList();
    }

    public JobResponseDto getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        return JobMapper.toDto(job);
    }
}
