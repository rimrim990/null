package com.project.undefined.acceptance.retrospect;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.DataUtils;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.common.dto.response.ErrorResponse;
import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.repository.StageRepository;
import com.project.undefined.retrospect.dto.request.CreateRetrospectRequest;
import com.project.undefined.retrospect.repository.RetrospectRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("Retrospect API 인수 테스트")
public class RetrospectTest extends AcceptanceTest {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private RetrospectRepository retrospectRepository;

    @Test
    @DisplayName("Retrospect를 생성한다.")
    void create() {
        // given
        final Long stageId = DataUtils.findAnyId(stageRepository, Stage::getId);
        final CreateRetrospectRequest request = new CreateRetrospectRequest(stageId, "test", "good",
            "bad", (short) 3, "not bad");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/retrospects/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        final Long retrospectId = RestAssuredUtils.parseLocationId(response, "/retrospects/");
        assertThat(retrospectRepository.existsById(retrospectId)).isTrue();
    }

    @Test
    @DisplayName("stageId가 null이면 400 상태를 반환한다.")
    void create_nullStageId_badRequest() {
        // given
        final CreateRetrospectRequest request = new CreateRetrospectRequest(null, "test", "good",
            "bad", (short) 3, "not bad");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/retrospects/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("stageId 은(는) 필수 입력 값입니다.");
    }

    @Test
    @DisplayName("stageId가 유효하지 않으면 400 상태를 반환한다.")
    void create_invalidStageId_badRequest() {
        // given
        final long invalidStageId = 1_000_000;
        final CreateRetrospectRequest request = new CreateRetrospectRequest(invalidStageId, "test", "good",
            "bad", (short) 3, "not bad");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/retrospects/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("일치하는 Stage가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("content가 공백이면 400 상태를 반환한다.")
    void create_blankContent_badRequest() {
        // given
        final Long stageId = DataUtils.findAnyId(stageRepository, Stage::getId);
        final CreateRetrospectRequest request = new CreateRetrospectRequest(stageId, "", "good",
            "bad", (short) 3, "not bad");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/retrospects/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("content 은(는) 필수 입력 값이며 공백을 제외한 문자를 하나 이상 포함해야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(shorts = {0, 6})
    @DisplayName("score가 1이상 5이하의 정수가 아니면 400 상태를 반환한다.")
    void create_invalidScore_badRequest(final short score) {
        // given
        final Long stageId = DataUtils.findAnyId(stageRepository, Stage::getId);
        final CreateRetrospectRequest request = new CreateRetrospectRequest(stageId, "test", "good",
            "bad", score, "not bad");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/retrospects/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("score 은(는) 5이하 1이상의 값이어야 합니다.");
    }
}
