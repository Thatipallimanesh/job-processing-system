package com.jobprocessing.job_service.inbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InboxRepository extends JpaRepository<InboxEvent, Long> {
    Optional<InboxEvent> findByEventId(String eventId);

    @Query("SELECT i FROM InboxEvent i WHERE i.status = 'PENDING' OR (i.status = 'PROCESSING' AND i.nextRetryAt <= CURRENT_TIMESTAMP)")
    List<InboxEvent> findReadyForProcessing();

    @Modifying
    @Query("UPDATE InboxEvent i SET i.status = 'PROCESSING' WHERE i.id = :id AND i.status = 'PENDING'")
    int markAsProcessing(Long id);
}
