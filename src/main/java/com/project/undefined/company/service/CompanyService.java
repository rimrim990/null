package com.project.undefined.company.service;

import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.dto.response.CompanyResponse;
import com.project.undefined.company.entity.Company;
import com.project.undefined.company.repository.CompanyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse create(final CreateCompanyRequest request) {
        final Company company = Company.of(request.getName(), request.getSeries(), request.getRegion());
        companyRepository.save(company);
        return CompanyResponse.from(company);
    }

    public List<CompanyResponse> getAll() {
        final List<Company> companies = companyRepository.findAll();
        return companies.stream()
            .map(CompanyResponse::from)
            .toList();
    }
}
