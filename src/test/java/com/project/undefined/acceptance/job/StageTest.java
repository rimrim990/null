package com.project.undefined.acceptance.job;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.common.dto.response.ErrorResponse;
import com.project.undefined.job.dto.request.CreateStageRequest;
import com.project.undefined.job.dto.response.StageResponse;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.repository.JobRepository;
import com.project.undefined.job.repository.StageRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@DisplayName("Stage API 인수 테스트")
public class StageTest extends AcceptanceTest {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Stage를 생성한다.")
    void create_created() {
        // given
        final Long jobId = getJobIds(Pageable.ofSize(1)).get(0);
        final CreateStageRequest request = new CreateStageRequest(jobId, "test");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/stages/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        final Long stageId = RestAssuredUtils.parseLocationId(response, "/stages/");
        assertThat(stageRepository.existsById(stageId)).isTrue();
    }

    @Test
    @DisplayName("name이 공백이면 400 상태를 반환한다.")
    void create_blankName_badRequest() throws JsonProcessingException {
        // given
        final Map<String, Object> request = new HashMap<>();
        request.put("jobId", "1");
        request.put("name", "");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(objectMapper.writeValueAsString(request))
            .contentType(ContentType.JSON)
            .post("/stages/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("name 은(는) 필수 입력 값이며 공백을 제외한 문자를 하나 이상 포함해야 합니다.");
    }

    @Test
    @DisplayName("jobId가 null이면 400 상태를 반환한다.")
    void create_nullJobId_badRequest() throws JsonProcessingException {
        // given
        final Map<String, Object> request = new HashMap<>();
        request.put("jobId", null);
        request.put("name", "test");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(objectMapper.writeValueAsString(request))
            .contentType(ContentType.JSON)
            .post("/stages/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("jobId 은(는) 필수 입력 값입니다.");
    }

    @Test
    @DisplayName("Stage 상세 정보를 조회한다.")
    void get() {
        // given
        final Long stageId = getStageIds(Pageable.ofSize(1)).get(0);

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/stages/" + stageId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final StageResponse stage = RestAssuredUtils.extract(response, StageResponse.class);
        assertThat(stage.getId()).isEqualTo(stageId);
    }

    @Test
    @DisplayName("id와 일치한 Stage가 없으면 400 상태를 반환한다.")
    void get_invalidStageId_badRequest() {
        // given
        final long notExistStageId = 1_000_000;

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/stages/" + notExistStageId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse stage = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(stage.getMessage()).isEqualTo("일치하는 Stage가 존재하지 않습니다.");
    }

    private List<Long> getStageIds(final Pageable pageable) {
        final Page<Stage> stages = stageRepository.findAll(pageable);
        return stages.getContent()
            .stream()
            .map(Stage::getId)
            .toList();
    }

    private List<Long> getJobIds(final Pageable pageable) {
        final Page<Job> jobs = jobRepository.findAll(pageable);
        return jobs.getContent()
            .stream()
            .map(Job::getId)
            .toList();
    }
}
