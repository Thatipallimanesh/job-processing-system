package com.jobprocessing.application_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JobClient {
    private final RestTemplate restTemplate;

    public void validateJobId(Long jobId) {
        String url = "http://localhost:8080/jobs/" + jobId;
        try {
            restTemplate.getForEntity(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Job ID");
        }
    }
}
