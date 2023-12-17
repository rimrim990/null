package com.project.undefined.retrospect.controller;

import com.project.undefined.retrospect.dto.response.RetrospectResponse;
import com.project.undefined.retrospect.service.RetrospectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/retrospects")
@RequiredArgsConstructor
public class RetrospectController {

    private final RetrospectService retrospectService;

    @GetMapping("/{id}")
    public RetrospectResponse get(@PathVariable final Long id) {
        return retrospectService.get(id);
    }
}
