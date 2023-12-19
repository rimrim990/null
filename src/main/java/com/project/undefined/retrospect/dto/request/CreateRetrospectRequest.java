package com.project.undefined.retrospect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

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

    @Range(min=1, max=5)
    private Short score;

    @NotBlank
    private String summary;
}
