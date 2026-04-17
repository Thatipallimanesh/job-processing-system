package com.jobprocessing.job_service.repository;

import com.jobprocessing.job_service.entity.Application;
import com.jobprocessing.job_service.utils.ProcessingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByJobIdAndCandidateEmailAndStatusNot(Long jobId, String candidateEmail, ProcessingStatus status);

    List<Application> findByJobIdAndStatusOrderByAtsScoreDesc(Long jobId, ProcessingStatus status, Pageable pageable);

    List<Application> findByJobIdAndStatusAndAtsScoreGreaterThanEqualOrderByAtsScoreDesc(Long jobId, ProcessingStatus status, int atsScore);
}
