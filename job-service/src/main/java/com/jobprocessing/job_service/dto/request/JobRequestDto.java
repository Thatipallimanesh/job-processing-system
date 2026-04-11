package com.jobprocessing.job_service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class JobRequestDto {
    private String title;
    private String description;
    private List<String> requiredSkills;
}
