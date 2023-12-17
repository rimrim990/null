package com.project.undefined.job.service;

import com.project.undefined.common.exception.StageException;
import com.project.undefined.job.dto.request.CreateStageRequest;
import com.project.undefined.job.dto.request.UpdateStageRequest;
import com.project.undefined.job.dto.response.StageResponse;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.entity.State;
import com.project.undefined.job.repository.StageRepository;
import com.project.undefined.retrospect.dto.request.CreateRetrospectRequest;
import com.project.undefined.retrospect.dto.response.RetrospectResponse;
import com.project.undefined.retrospect.service.RetrospectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {

    private final JobService jobService;
    private final RetrospectService retrospectService;
    private final StageRepository stageRepository;

    public StageResponse create(final CreateStageRequest request) {
        final Job job = jobService.getOne(request.getJobId());
        final Stage stage = Stage.of(request.getName(), job);
        stageRepository.save(stage);
        return StageResponse.from(stage);
    }

    public RetrospectResponse attachRetrospect(final Long id, final CreateRetrospectRequest request) {
        final Stage stage = getOne(id);
        final RetrospectResponse retrospectResponse = retrospectService.create(request);
        stage.attachRetrospect(retrospectResponse.getId());
        return retrospectResponse;
    }

    public StageResponse get(final Long id) {
        final Stage stage = getOne(id);
        return StageResponse.from(stage);
    }

    public List<StageResponse> getJobStages(final Long jobId) {
        final Job job = jobService.getOne(jobId);
        final List<Stage> stages = stageRepository.findByJob(job);
        return stages.stream()
            .map(StageResponse::from)
            .toList();
    }

    public StageResponse updateState(final Long id, final UpdateStageRequest request) {
        final Stage stage = getOne(id);
        stage.updateState(State.from(request.getState()));
        return StageResponse.from(stage);
    }

    public void validate(final Long id) {
        if (!stageRepository.existsById(id)) {
            throw new StageException("일치하는 Stage가 존재하지 않습니다.");
        }
    }

    private Stage getOne(final Long id) {
        return stageRepository.findById(id)
            .orElseThrow(() -> new StageException("일치하는 Stage가 존재하지 않습니다."));
    }
}
