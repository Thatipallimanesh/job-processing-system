package com.jobprocessing.job_service.utils;

import com.jobprocessing.job_service.dto.response.ApplicationResponseDto;
import com.jobprocessing.job_service.entity.Application;

public class ApplicationMapper {
    public static ApplicationResponseDto toDto(Application application) {
        return new ApplicationResponseDto(
                application.getCandidateName(),
                application.getCandidateEmail(),
                application.getAtsScore()
        );
    }
}
