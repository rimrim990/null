package com.project.undefined.job.controller;

import com.project.undefined.job.dto.response.JobResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/jobs")
public class JobController {

    @GetMapping("/")
    public List<JobResponse> getAll() {
        return List.of(new JobResponse(1L, "company", "position", List.of()));
    }
}
