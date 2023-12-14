package com.project.undefined.company.dto.request;

import com.project.undefined.common.validation.Enum;
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

    @NotBlank
    @Enum(target = Series.class)
    private String series;

    @NotBlank
    @Enum(target = Region.class)
    private String region;
}
