package com.project.undefined.retrospect.dto.response;

import com.project.undefined.retrospect.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrospectResponse {

    private Long id;
    private Long stageId;
    private String content;
    private String goodPoint;
    private String badPoint;
    private Score score;
    private String summary;
}
