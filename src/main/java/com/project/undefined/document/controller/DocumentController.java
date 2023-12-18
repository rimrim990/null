package com.project.undefined.document.controller;

import com.project.undefined.document.dto.request.CreateDocumentRequest;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/documents")
public class DocumentController {

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateDocumentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/documents/1"))
            .build();
    }
}
