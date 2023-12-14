package com.project.undefined.company.dto.response;

import com.project.undefined.company.entity.Company;
import com.project.undefined.company.entity.Region;
import com.project.undefined.company.entity.Series;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyResponse {

    private Long id;
    private String name;
    private Series series;
    private Region region;

    public static CompanyResponse from(final Company company) {
        return new CompanyResponse(
            company.getId(),
            company.getName(),
            company.getSeries(),
            company.getRegion()
        );
    }
}
