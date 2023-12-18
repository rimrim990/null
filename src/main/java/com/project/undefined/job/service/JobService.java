package com.project.undefined.job.service;

import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.common.exception.JobException;
import com.project.undefined.company.service.CompanyService;
import com.project.undefined.job.dto.request.CreateJobRequest;
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
    private final CompanyService companyService;

    public JobResponse create(final CreateJobRequest request) {
        companyService.validate(request.getCompanyId());
        final Job job = Job.of(request.getCompanyId(), request.getPosition());
        jobRepository.save(job);
        return JobResponse.from(job);
    }

    public List<JobResponse> getAll() {
        final List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
            .map(JobResponse::from)
            .toList();
    }

    public JobResponse get(final Long id) {
        final Job job = getOne(id);
        return JobResponse.from(job);
    }

    public void validate(final Long id) {
        if (!jobRepository.existsById(id)) {
            throw new JobException(ErrorCode.NON_MATCH_JOB);
        }
    }

    Job getOne(final Long id) {
        return jobRepository.findById(id)
            .orElseThrow(() -> new JobException(ErrorCode.NON_MATCH_JOB));
    }
}
