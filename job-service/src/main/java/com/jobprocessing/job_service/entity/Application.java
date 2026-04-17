package com.jobprocessing.job_service.entity;

import com.jobprocessing.job_service.utils.ProcessingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "candidate_email"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;

    private String candidateName;

    private String candidateEmail;

    private String resumeLink;

    private Integer atsScore;

    @Enumerated(EnumType.STRING)
    private ProcessingStatus status;

    private LocalDateTime createdAt;
}
