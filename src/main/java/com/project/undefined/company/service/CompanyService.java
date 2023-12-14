package com.project.undefined.company.service;

import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.dto.response.CompanyResponse;
import com.project.undefined.company.entity.Company;
import com.project.undefined.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository jobRepository;

    public CompanyResponse create(final CreateCompanyRequest request) {
        final Company company = Company.of(request.getName(), request.getSeries(), request.getRegion());
        jobRepository.save(company);
        return CompanyResponse.from(company);
    }
}
