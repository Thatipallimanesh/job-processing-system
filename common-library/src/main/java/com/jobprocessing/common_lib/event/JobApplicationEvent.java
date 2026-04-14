package com.jobprocessing.common_lib.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationEvent {
    private Long jobId;
    private String candidateName;
    private String candidateEmail;
    private String resumeLink;
}
