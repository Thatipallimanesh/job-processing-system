package com.jobprocessing.job_service.service;

import com.jobprocessing.job_service.dto.response.ApplicationResponseDto;
import com.jobprocessing.job_service.repository.ApplicationRepository;
import com.jobprocessing.job_service.utils.ApplicationMapper;
import com.jobprocessing.job_service.utils.ProcessingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public boolean checkApplicationExists(Long jobId, String candidateEmail) {
        return applicationRepository.existsByJobIdAndCandidateEmailAndStatusNot(jobId, candidateEmail, ProcessingStatus.FAILED);
    }

    public List<ApplicationResponseDto> getTopApplications(Long jobId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return applicationRepository.findByJobIdAndStatusOrderByAtsScoreDesc(jobId, ProcessingStatus.COMPLETED, pageable)
                .stream()
                .map(ApplicationMapper::toDto)
                .toList();
    }

    public List<ApplicationResponseDto> getApplicantsAboveScore(Long jobId, int minScore) {
        return applicationRepository.findByJobIdAndStatusAndAtsScoreGreaterThanEqualOrderByAtsScoreDesc(jobId, ProcessingStatus.COMPLETED, minScore)
                .stream()
                .map(ApplicationMapper::toDto)
                .toList();
    }
}
