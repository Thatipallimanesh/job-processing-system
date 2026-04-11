package com.jobprocessing.job_service.utils;

import com.jobprocessing.job_service.dto.request.JobRequestDto;
import com.jobprocessing.job_service.dto.response.JobResponseDto;
import com.jobprocessing.job_service.entity.Job;

public class JobMapper {
    public static Job toEntity(JobRequestDto  jobRequestDto) {
        Job job = new Job();
        job.setTitle(jobRequestDto.getTitle());
        job.setDescription(jobRequestDto.getDescription());
        job.setRequiredSkills(jobRequestDto.getRequiredSkills());
        return job;
    }

    public static JobResponseDto toDto(Job job) {
        return new JobResponseDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getRequiredSkills(),
                job.getCreatedAt()
        );
    }
}
