package com.project.undefined.job.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateJobRequest {

    @NotNull
    private Long companyId;

    @NotBlank
    private String position;
}
