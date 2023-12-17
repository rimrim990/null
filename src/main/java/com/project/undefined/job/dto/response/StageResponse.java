package com.project.undefined.job.dto.response;

import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.entity.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StageResponse {

    private Long id;
    private Long retrospectId;
    private String name;
    private State state;

    public static StageResponse from(final Stage stage) {
        return new StageResponse(stage.getId(), stage.getRetrospectId(), stage.getName(), stage.getState());
    }
}
