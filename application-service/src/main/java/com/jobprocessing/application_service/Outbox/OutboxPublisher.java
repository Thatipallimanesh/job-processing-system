package com.jobprocessing.application_service.Outbox;

import com.jobprocessing.application_service.entity.JobApplicationEvent;
import com.jobprocessing.application_service.service.KafkaProducerService;
import com.jobprocessing.application_service.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaProducerService kafkaProducerService;
    private final JsonUtil jsonUtil;

    @Scheduled(fixedDelay = 5000) // Run every 5 seconds
    public void publishOutboxEvents() {
        System.out.println("Checking for pending outbox events...");
        List<OutboxEvent> pendingEvents = outboxRepository.findAllByStatus(OutboxStatus.PENDING);
        System.out.println("Found " + pendingEvents.size() + " pending events.");
        for (OutboxEvent outboxEvent : pendingEvents) {
            try {
                JobApplicationEvent event = jsonUtil.convertFromJson(outboxEvent.getPayload(), JobApplicationEvent.class);
                kafkaProducerService.sendApplication(event);
                outboxEvent.setStatus(OutboxStatus.SENT);
                System.out.println("outbox status changed to sent");
                outboxRepository.save(outboxEvent);
            } catch (Exception e) {
                // try later.
                System.out.println(e.getMessage());
            }
        }
    }
}
