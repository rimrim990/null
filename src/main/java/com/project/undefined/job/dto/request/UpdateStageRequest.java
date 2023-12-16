package com.project.undefined.job.dto.request;

import static com.project.undefined.job.entity.Stage.*;

import com.project.undefined.common.validation.Enum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStageRequest {

    @Enum(target = State.class)
    private String state;
}
