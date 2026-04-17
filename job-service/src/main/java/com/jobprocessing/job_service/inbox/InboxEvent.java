package com.jobprocessing.job_service.inbox;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inbox_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String eventId;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private InboxStatus status;

    private int retryCount;

    private LocalDateTime nextRetryAt;

    private LocalDateTime createdAt;
}
