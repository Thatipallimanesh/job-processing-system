package com.jobprocessing.application_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationEvent {
    private Long jobId;
    private String candidateName;
    private String candidateEmail;
    private String resumeLink;
}
