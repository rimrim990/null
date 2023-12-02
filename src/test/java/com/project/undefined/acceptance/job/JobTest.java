package com.project.undefined.acceptance.job;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.job.dto.response.JobResponse;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.repository.JobRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@DisplayName("Job API 인수 테스트")
public class JobTest extends AcceptanceTest {

    @Autowired
    JobRepository jobRepository;

    @Test
    @DisplayName("등록된 모든 Job 목록을 조회한다.")
    void getAll_returnJobList() {
        // when
        ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/jobs/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<JobResponse> jobs = RestAssuredUtils.extractAsList(response, JobResponse.class);
        assertThat(jobs).hasSize(3);
    }

    @Test
    @DisplayName("id와 일치한 Job의 세부 정보를 조회한다.")
    void get_returnJob() {
        // given
        final Long jobId = getJobIds(1).get(0);

        // when
        ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/jobs/" + jobId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        JobResponse job = RestAssuredUtils.extract(response, JobResponse.class);
        assertThat(job).hasFieldOrPropertyWithValue("id", jobId);
    }

    private List<Long> getJobIds(final int size) {
        final Pageable pageable = Pageable.ofSize(size);
        final Page<Job> jobs = jobRepository.findAll(pageable);
        return jobs.getContent()
            .stream()
            .map(Job::getId)
            .toList();
    }
}
