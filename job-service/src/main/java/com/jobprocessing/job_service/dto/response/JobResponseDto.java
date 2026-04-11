package com.jobprocessing.job_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<String> requiredSkills;
    private LocalDateTime createdAt;

    public JobResponseDto(Long id, String title, String description, List<String> requiredSkills, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.createdAt = createdAt;
    }
}
