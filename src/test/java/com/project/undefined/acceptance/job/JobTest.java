package com.project.undefined.acceptance.job;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.job.dto.response.JobResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Job API 인수 테스트")
public class JobTest extends AcceptanceTest {

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
}
