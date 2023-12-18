package com.project.undefined.document.service;

import com.project.undefined.document.dto.request.CreateDocumentRequest;
import com.project.undefined.document.dto.response.DocumentResponse;
import com.project.undefined.document.entity.Document;
import com.project.undefined.document.repository.DocumentRepository;
import com.project.undefined.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final JobService jobService;
    private final DocumentRepository documentRepository;

    public DocumentResponse create(final CreateDocumentRequest request) {
        jobService.validate(request.getJobId());

        Document document = Document.of(request.getJobId(), request.getContent());
        documentRepository.save(document);
        return DocumentResponse.from(document);
    }
}
