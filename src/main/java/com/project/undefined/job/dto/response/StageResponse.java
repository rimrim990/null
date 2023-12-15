package com.project.undefined.job.dto.response;

import com.project.undefined.job.entity.Stage.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StageResponse {

    private Long id;
    private String name;
    private State state;
}
