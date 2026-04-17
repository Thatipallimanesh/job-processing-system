package com.jobprocessing.job_service.service;

import com.jobprocessing.job_service.repository.ApplicationRepository;
import com.jobprocessing.job_service.utils.ProcessingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public boolean checkApplicationExists(Long jobId, String candidateEmail) {
        return applicationRepository.existsByJobIdAndCandidateEmailAndStatusNot(jobId, candidateEmail, ProcessingStatus.FAILED);
    }
}
