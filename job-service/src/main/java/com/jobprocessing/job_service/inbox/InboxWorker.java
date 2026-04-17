package com.jobprocessing.job_service.inbox;

import com.jobprocessing.common_lib.event.JobApplicationEvent;
import com.jobprocessing.job_service.service.ApplicationProcessingService;
import com.jobprocessing.job_service.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class InboxWorker {
    private final JsonUtil jsonUtil;
    private final ApplicationProcessingService applicationProcessingService;
    private final ExecutorService executorService;

    private final InboxRepository inboxRepository;

    private static final int MAX_RETRY = 3;

    @Scheduled(fixedDelay = 5000) // Run every 5 seconds
    public void processInbox() {
        List<InboxEvent> pendingEvents = inboxRepository.findReadyForProcessing();

        for (InboxEvent event : pendingEvents) {
            executorService.submit(() -> handleInboxEvent(event));
        }
    }

    private void handleInboxEvent(InboxEvent inboxEvent) {
        // thread-safe update
        int updated = inboxRepository.markAsProcessing(inboxEvent.getId());
        if (updated == 0) return;

        try {
            JobApplicationEvent event = jsonUtil.convertFromJson(inboxEvent.getPayload(), JobApplicationEvent.class);
            applicationProcessingService.processApplication(event);
            inboxEvent.setStatus(InboxStatus.DONE);
        } catch (Exception e) {
            int retryCount = inboxEvent.getRetryCount() + 1;
            inboxEvent.setRetryCount(retryCount);
            if (retryCount >= MAX_RETRY) {
                System.out.println("Saved to DLQ");
                // DLQ Candidate
                inboxEvent.setStatus(InboxStatus.FAILED);
            } else {
                System.out.println("Saved to retry Queue");
                inboxEvent.setStatus(InboxStatus.PENDING);
                // Exponential Backoff
                long delay = (long) (Math.pow(2, retryCount) * 5);
                inboxEvent.setNextRetryAt(LocalDateTime.now().plusSeconds(delay));
            }
        }
        inboxRepository.save(inboxEvent);
    }
}
