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

    @PostMapping("/related/{stageId}")
    public ResponseEntity<Void> createRelated(@PathVariable final Long stageId,
            @Valid @RequestBody CreateRetrospectRequest request) {
        final RetrospectResponse response = retrospectService.create(stageId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/retrospects/" + response.getId()))
            .build();
    }

    @GetMapping("/{id}")
    public RetrospectResponse get(@PathVariable final Long id) {
        return retrospectService.get(id);
    }
}
