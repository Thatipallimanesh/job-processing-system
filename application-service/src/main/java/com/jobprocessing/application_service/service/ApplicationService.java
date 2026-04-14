package com.jobprocessing.application_service.service;

import com.jobprocessing.application_service.Outbox.OutboxStatus;
import com.jobprocessing.application_service.entity.Application;
import com.jobprocessing.application_service.Outbox.OutboxEvent;
import com.jobprocessing.application_service.repository.ApplicationRepository;
import com.jobprocessing.application_service.Outbox.OutboxRepository;
import com.jobprocessing.application_service.utils.JsonUtil;
import com.jobprocessing.common_lib.event.JobApplicationEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final JobClient jobClient;
    private final FileStorageService fileStorageService;
    private final JsonUtil jsonUtil;

    private final ApplicationRepository applicationRepository;
    private final OutboxRepository outboxRepository;

    @Transactional
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

        JobApplicationEvent event = new JobApplicationEvent(jobId, candidateName, candidateEmail, resumeLink);

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setEventType("JOB_APPLICATION");
        outboxEvent.setPayload(jsonUtil.convertToJson(event));
        outboxEvent.setStatus(OutboxStatus.PENDING);
        outboxEvent.setCreatedAt(LocalDateTime.now());
        outboxRepository.save(outboxEvent);

        return "Application submitted successfully!";
    }
}
