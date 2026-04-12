package com.jobprocessing.application_service.service;

import com.jobprocessing.application_service.entity.ApplicationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, ApplicationEvent> kafkaTemplate;

    public void sendApplication(ApplicationEvent event) {
        kafkaTemplate.send("job-applications", event);
    }
}
