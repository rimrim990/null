package com.project.undefined.retrospect.controller;

import com.project.undefined.retrospect.dto.request.CreateRetrospectRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/retrospects")
@RequiredArgsConstructor
public class RetrospectController {

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody final CreateRetrospectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .location(URI.create("/retrospects/1"))
            .build();
    }
}
