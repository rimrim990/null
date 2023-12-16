package com.project.undefined.retrospect.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRetrospectRequest {

    @NotNull
    private Long stageId;

    @NotBlank
    private String content;

    @NotBlank
    private String goodPoint;

    @NotBlank
    private String badPoint;

    @Min(1)
    @Max(5)
    private Short score;

    @NotBlank
    private String summary;
}
