package com.jobprocessing.job_service.service;

import com.jobprocessing.job_service.dto.response.JobResponseDto;
import com.jobprocessing.job_service.entity.Application;
import com.jobprocessing.job_service.repository.ApplicationRepository;
import com.jobprocessing.job_service.utils.ProcessingStatus;
import com.jobprocessing.common_lib.event.JobApplicationEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationProcessingService {
    private final ResumeParser resumeParser;
    private final JobService jobService;

    private final ApplicationRepository applicationRepository;

    @Async
    @Transactional
    public void processApplication(JobApplicationEvent event) {
        JobResponseDto job = jobService.getJobById(event.getJobId());

        Application application = new Application();
        application.setJobId(event.getJobId());
        application.setCandidateName(event.getCandidateName());
        application.setCandidateEmail(event.getCandidateEmail());
        application.setResumeLink(event.getResumeLink());
        application.setStatus(ProcessingStatus.PROCESSING);
        application.setCreatedAt(LocalDateTime.now());
        application = applicationRepository.save(application);

        try {
            String resumeText = resumeParser.extractText(event.getResumeLink());
            int atsScore = calculateAtsScore(resumeText, job.getRequiredSkills());
            application.setAtsScore(atsScore);
            application.setStatus(ProcessingStatus.COMPLETED);
        } catch (Exception e) {
            application.setStatus(ProcessingStatus.FAILED);
            throw e;
        } finally {
            applicationRepository.save(application);
        }
    }

    private int calculateAtsScore(String resumeText, List<String> requiredSkills) {
        int matchedSkills = 0;
        for (String requiredSkill : requiredSkills) {
            if (resumeText.toLowerCase().contains(requiredSkill.toLowerCase())) {
                matchedSkills++;
            }
        }
        return (matchedSkills * 100 / requiredSkills.size());
    }
}
