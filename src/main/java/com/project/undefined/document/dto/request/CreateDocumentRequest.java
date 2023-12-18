package com.project.undefined.document.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateDocumentRequest {

    @NotNull
    private Long jobId;

    @NotBlank
    private String content;
}
