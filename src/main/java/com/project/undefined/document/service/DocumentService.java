package com.project.undefined.document.service;

import com.project.undefined.common.exception.DocumentException;
import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.document.dto.request.CreateDocumentRequest;
import com.project.undefined.document.dto.response.DocumentResponse;
import com.project.undefined.document.entity.Document;
import com.project.undefined.document.repository.DocumentRepository;
import com.project.undefined.job.service.JobService;
import java.util.Optional;
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

    public DocumentResponse get(final Long id) {
        final Document document = getOne(id);
        return DocumentResponse.from(document);
    }

    public Optional<DocumentResponse> getRelated(final Long jobId) {
        jobService.validate(jobId);

        return documentRepository.findByJobId(jobId)
            .map(DocumentResponse::from);
    }

    private Document getOne(final Long id) {
        return documentRepository.findById(id)
            .orElseThrow(() -> new DocumentException(ErrorCode.NON_MATCH_DOCUMENT));
    }
}
