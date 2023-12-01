package com.project.undefined.job.service;

import com.project.undefined.job.dto.response.JobResponse;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.repository.JobRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<JobResponse> getAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
            .map(JobResponse::from)
            .toList();
    }
}
