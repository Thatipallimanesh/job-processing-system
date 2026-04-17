package com.jobprocessing.job_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDto {
    private String candidateName;
    private String candidateEmail;
    private Integer atsScore;
}
