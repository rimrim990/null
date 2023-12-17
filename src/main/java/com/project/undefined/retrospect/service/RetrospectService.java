package com.project.undefined.retrospect.service;

import com.project.undefined.common.exception.RetrospectException;
import com.project.undefined.job.service.StageService;
import com.project.undefined.retrospect.dto.request.CreateRetrospectRequest;
import com.project.undefined.retrospect.dto.response.RetrospectResponse;
import com.project.undefined.retrospect.entity.Retrospect;
import com.project.undefined.retrospect.entity.Score;
import com.project.undefined.retrospect.repository.RetrospectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrospectService {

    private final StageService stageService;
    private final RetrospectRepository retrospectRepository;

    public RetrospectResponse create(final CreateRetrospectRequest request) {
        stageService.validate(request.getStageId());

        final Retrospect retrospect = mapFrom(request);
        retrospectRepository.save(retrospect);
        return RetrospectResponse.from(retrospect);
    }

    public RetrospectResponse get(final Long id) {
        final Retrospect retrospect = getOne(id);
        return RetrospectResponse.from(retrospect);
    }

    private Retrospect getOne(final Long id) {
        return retrospectRepository.findById(id)
            .orElseThrow(() -> new RetrospectException("일치하는 Retrospect가 존재하지 않습니다."));
    }

    private Retrospect mapFrom(final CreateRetrospectRequest request) {
        return Retrospect.builder()
            .content(request.getContent())
            .goodPoint(request.getGoodPoint())
            .badPoint(request.getBadPoint())
            .summary(request.getSummary())
            .stageId(request.getStageId())
            .score(new Score(request.getScore()))
            .build();
    }
}
