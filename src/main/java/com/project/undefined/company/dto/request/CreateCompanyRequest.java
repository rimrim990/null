package com.project.undefined.company.dto.request;

import com.project.undefined.company.entity.Region;
import com.project.undefined.company.entity.Series;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCompanyRequest {

    @NotBlank
    private String name;
    private Series series;
    private Region region;
}
