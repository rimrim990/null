package com.project.undefined.retrospect.controller;

import com.project.undefined.retrospect.dto.request.CreateRetrospectRequest;
import com.project.undefined.retrospect.dto.response.RetrospectResponse;
import com.project.undefined.retrospect.service.RetrospectService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/retrospects")
@RequiredArgsConstructor
public class RetrospectController {

    private final RetrospectService retrospectService;

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody final CreateRetrospectRequest request) {
        final RetrospectResponse retrospectResponse = retrospectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .location(URI.create("/retrospects/" + retrospectResponse.getId()))
            .build();
    }

    @GetMapping("/{id}")
    public RetrospectResponse get(@PathVariable final Long id) {
        return null;
    }
}
