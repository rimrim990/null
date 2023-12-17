package com.project.undefined.company.service;

import com.project.undefined.common.exception.CompanyException;
import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.dto.response.CompanyResponse;
import com.project.undefined.company.entity.Company;
import com.project.undefined.company.entity.Region;
import com.project.undefined.company.entity.Series;
import com.project.undefined.company.repository.CompanyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse create(final CreateCompanyRequest request) {
        final Series series = Series.valueOf(request.getSeries());
        final Region region = Region.valueOf(request.getRegion());
        final Company company = Company.of(request.getName(), series, region);

        companyRepository.save(company);
        return CompanyResponse.from(company);
    }

    public List<CompanyResponse> getAll() {
        final List<Company> companies = companyRepository.findAll();
        return companies.stream()
            .map(CompanyResponse::from)
            .toList();
    }

    public CompanyResponse get(final Long id) {
        final Company company = getOne(id);
        return CompanyResponse.from(company);
    }

    public void validate(final Long id) {
        if (!companyRepository.existsById(id)) {
            throw new CompanyException(ErrorCode.NON_MATCH_COMPANY);
        }
    }

    Company getOne(final Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new CompanyException(ErrorCode.NON_MATCH_COMPANY));
    }
}
