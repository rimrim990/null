package com.project.undefined.retrospect.dto.response;

import com.project.undefined.retrospect.entity.Retrospect;
import com.project.undefined.retrospect.entity.Score;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class RetrospectResponse {

    private Long id;
    private Long stageId;
    private String content;
    private String goodPoint;
    private String badPoint;
    private Score score;
    private String summary;

    public static RetrospectResponse from(final Retrospect retrospect) {
        return RetrospectResponse.builder()
            .id(retrospect.getId())
            .stageId(retrospect.getStageId())
            .content(retrospect.getContent())
            .goodPoint(retrospect.getGoodPoint())
            .badPoint(retrospect.getBadPoint())
            .summary(retrospect.getSummary())
            .score(retrospect.getScore())
            .build();
    }
}
