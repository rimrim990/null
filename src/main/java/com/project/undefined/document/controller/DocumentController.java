package com.project.undefined.document.controller;

import com.project.undefined.document.dto.request.CreateDocumentRequest;
import com.project.undefined.document.dto.response.DocumentResponse;
import com.project.undefined.document.service.DocumentService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/documents")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateDocumentRequest request) {
        final DocumentResponse documentResponse = documentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/documents/" + documentResponse.getId()))
            .build();
    }

    @GetMapping("/{id}")
    public DocumentResponse get(@PathVariable final Long id) {
        return documentService.get(id);
    }
}
