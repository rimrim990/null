package com.project.undefined.document.dto.response;

import com.project.undefined.document.entity.Document;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentResponse {

    private Long id;
    private Long jobId;
    private String content;

    public static DocumentResponse from(final Document document) {
        return new DocumentResponse(document.getId(), document.getJobId(), document.getContent());
    }
}
