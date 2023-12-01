package com.project.undefined.job.controller;

import com.project.undefined.job.dto.response.JobResponse;
import com.project.undefined.job.service.JobService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/")
    public List<JobResponse> getAll() {
        return jobService.getAll();
    }
}
