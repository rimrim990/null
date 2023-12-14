package com.project.undefined.company.controller;

import com.project.undefined.company.dto.request.CreateCompanyRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/companies")
@RequiredArgsConstructor
public class CompanyController {

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody final CreateCompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .location(URI.create("/companies/1"))
            .build();
    }
}
