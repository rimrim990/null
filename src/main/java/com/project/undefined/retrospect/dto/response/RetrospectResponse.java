package com.project.undefined.retrospect.dto.response;

import com.project.undefined.retrospect.entity.Retrospect;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class RetrospectResponse {

    private Long id;
    private String content;
    private String goodPoint;
    private String badPoint;
    private Short score;
    private String summary;

    public static RetrospectResponse from(final Retrospect retrospect) {
        return RetrospectResponse.builder()
            .id(retrospect.getId())
            .content(retrospect.getContent())
            .goodPoint(retrospect.getGoodPoint())
            .badPoint(retrospect.getBadPoint())
            .summary(retrospect.getSummary())
            .score(retrospect.getScore().getValue())
            .build();
    }
}
