package com.jobprocessing.application_service.service;

import com.jobprocessing.application_service.entity.Application;
import com.jobprocessing.application_service.entity.ApplicationEvent;
import com.jobprocessing.application_service.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final JobClient jobClient;
    private final KafkaProducerService kafkaProducerService;
    private final FileStorageService fileStorageService;

    private final ApplicationRepository applicationRepository;

    public String apply(Long jobId, String candidateName, String candidateEmail, MultipartFile resume) {
        jobClient.validateJobId(jobId);

        String resumeLink = fileStorageService.saveResume(resume);

        Application application = new Application();
        application.setJobId(jobId);
        application.setCandidateName(candidateName);
        application.setCandidateEmail(candidateEmail);
        application.setResumeLink(resumeLink);
        application.setCreatedAt(LocalDateTime.now());
        applicationRepository.save(application);

        ApplicationEvent event = new ApplicationEvent(jobId, candidateName, candidateEmail, resumeLink);
        kafkaProducerService.sendApplication(event);

        return "Application submitted successfully!";
    }
}
