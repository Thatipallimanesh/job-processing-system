package com.jobprocessing.application_service.service;

import com.jobprocessing.common_lib.exception.BadRequestException;
import com.jobprocessing.common_lib.exception.ExternalServiceException;
import com.jobprocessing.common_lib.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JobClient {
    private final RestTemplate restTemplate;

    public void validateJobId(Long jobId) {
        String url = "http://localhost:8080/jobs/" + jobId;
        try {
            restTemplate.getForEntity(url, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Job not found with id: " + jobId);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException("Invalid Request");
        } catch (HttpServerErrorException | ResourceAccessException e) {
            throw new ExternalServiceException("Job Service is currently unavailable. Please try again later.");
        } catch (Exception e) {
            throw new ExternalServiceException("Unexpected Error while validating job");
        }
    }

    public void validateDuplicateApplication(Long jobId, String candidateEmail) {
        String url = "http://localhost:8080/applications/exists?jobId=" + jobId + "&candidateEmail=" + candidateEmail;
        Boolean exists = restTemplate.getForEntity(url, Boolean.class).getBody();
        if(Boolean.TRUE.equals(exists)) {
            throw new BadRequestException("You have already applied for this job");
        }
    }
}
