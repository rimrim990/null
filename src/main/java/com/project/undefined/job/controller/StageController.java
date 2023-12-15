package com.project.undefined.job.controller;

import com.project.undefined.job.dto.request.CreateStageRequest;
import com.project.undefined.job.dto.response.StageResponse;
import com.project.undefined.job.service.StageService;
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
@RequestMapping("${apiPrefix}/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody final CreateStageRequest request) {
        final StageResponse stageResponse = stageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .location(URI.create("/stages/" + stageResponse.getId()))
            .build();
    }

    @GetMapping("/{id}")
    public StageResponse get(@PathVariable final Long id) {
        return stageService.get(id);
    }
}
