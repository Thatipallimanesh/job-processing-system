package com.jobprocessing.application_service.service;

import com.jobprocessing.common_lib.event.JobApplicationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, JobApplicationEvent> kafkaTemplate;

    public void sendApplication(JobApplicationEvent event) {
        kafkaTemplate.send("job-applications", event);
    }
}
