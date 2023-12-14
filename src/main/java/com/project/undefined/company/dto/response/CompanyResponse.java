package com.project.undefined.company.dto.response;

import com.project.undefined.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyResponse {

    private Long id;
    private String name;
    private String series;
    private String region;

    public static CompanyResponse from(final Company company) {
        return new CompanyResponse(
            company.getId(),
            company.getName(),
            company.getSeries().toString(),
            company.getRegion().toString()
        );
    }
}
