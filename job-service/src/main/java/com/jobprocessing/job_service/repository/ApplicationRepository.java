package com.jobprocessing.job_service.repository;

import com.jobprocessing.job_service.entity.Application;
import com.jobprocessing.job_service.utils.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByJobIdAndCandidateEmailAndStatusNot(Long jobId, String candidateEmail, ProcessingStatus status);
}
