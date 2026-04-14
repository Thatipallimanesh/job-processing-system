package com.jobprocessing.job_service.service;

import com.jobprocessing.common_lib.event.JobApplicationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final ApplicationProcessingService applicationProcessingService;

    @KafkaListener(topics = "job-applications", groupId = "job-group")
    public void consumeApplications(JobApplicationEvent event) {
        // Process the incoming job application event
        applicationProcessingService.processApplication(event);
    }
}
