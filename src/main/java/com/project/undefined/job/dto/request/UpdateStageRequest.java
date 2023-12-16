package com.project.undefined.job.dto.request;

import com.project.undefined.common.validation.Enum;
import com.project.undefined.job.entity.State;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStageRequest {

    @NotBlank
    @Enum(target = State.class)
    private String state;
}
