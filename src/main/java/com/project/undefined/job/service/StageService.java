package com.project.undefined.job.service;

import com.project.undefined.common.exception.JobException;
import com.project.undefined.common.exception.StageException;
import com.project.undefined.job.dto.request.CreateStageRequest;
import com.project.undefined.job.dto.response.StageResponse;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.repository.JobRepository;
import com.project.undefined.job.repository.StageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {

    private final JobRepository jobRepository;
    private final StageRepository stageRepository;


    public StageResponse create(final CreateStageRequest request) {
        final Job job = jobRepository.findById(request.getJobId())
            .orElseThrow(() -> new JobException("일치하는 Job이 존재하지 않습니다"));

        final Stage stage = Stage.of(request.getName(), job);
        stageRepository.save(stage);
        return StageResponse.from(stage);
    }

    public StageResponse get(final Long id) {
        final Stage stage = stageRepository.findById(id)
            .orElseThrow(() -> new StageException("일치하는 Stage가 존재하지 않습니다."));

        return StageResponse.from(stage);
    }

    public List<StageResponse> getJobStages(final Long jobId) {
        final Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new JobException("일치하는 Job이 존재하지 않습니다."));

        final List<Stage> stages = stageRepository.findByJob(job);
        return stages.stream()
            .map(StageResponse::from)
            .toList();
    }
}
