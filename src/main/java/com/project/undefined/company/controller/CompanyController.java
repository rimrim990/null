package com.project.undefined.company.controller;

import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.dto.response.CompanyResponse;
import com.project.undefined.company.service.CompanyService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody final CreateCompanyRequest request) {
        final CompanyResponse response = companyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .location(URI.create("/companies/" + response.getId()))
            .build();
    }

    @GetMapping("/")
    public List<CompanyResponse> getAll() {
        return List.of();
    }
}
