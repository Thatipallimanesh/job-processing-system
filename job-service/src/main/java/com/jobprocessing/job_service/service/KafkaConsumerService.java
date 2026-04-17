package com.jobprocessing.job_service.service;

import com.jobprocessing.common_lib.event.JobApplicationEvent;
import com.jobprocessing.job_service.inbox.InboxEvent;
import com.jobprocessing.job_service.inbox.InboxRepository;
import com.jobprocessing.job_service.inbox.InboxStatus;
import com.jobprocessing.job_service.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final JsonUtil jsonUtil;

    private final InboxRepository inboxRepository;

    @KafkaListener(topics = "job-applications", groupId = "job-group")
    public void consumeApplications(JobApplicationEvent event) {
        // idempotency check
        if(inboxRepository.findByEventId(event.getEventId()).isPresent()) {
            // Duplicate event, ignore
            return;
        }

        InboxEvent inboxEvent = new InboxEvent();
        inboxEvent.setEventId(event.getEventId());
        inboxEvent.setPayload(jsonUtil.convertToJson(event));
        inboxEvent.setStatus(InboxStatus.PENDING);
        inboxEvent.setRetryCount(0);
        inboxEvent.setNextRetryAt(LocalDateTime.now());
        inboxEvent.setCreatedAt(LocalDateTime.now());
        inboxRepository.save(inboxEvent);
    }
}
