package com.project.undefined.job.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateJobRequest {

    private Long companyId;
    private String position;
}
