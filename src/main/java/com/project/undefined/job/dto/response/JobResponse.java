package com.project.undefined.job.dto.response;

import com.project.undefined.job.entity.Job;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobResponse {

    private Long jobId;
    private String company;
    private String position;
    private List<String> stages;

    public static JobResponse from(final Job job) {
        return new JobResponse(
            job.getId(),
            "company",
            job.getPosition(),
            List.of()
        );
    }
}
