package com.project.undefined.job.dto.response;

import com.project.undefined.job.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobResponse {

    private Long id;
    private Long companyId;
    private String position;

    public static JobResponse from(final Job job) {
        return new JobResponse(
            job.getId(),
            job.getCompanyId(),
            job.getPosition()
        );
    }
}
