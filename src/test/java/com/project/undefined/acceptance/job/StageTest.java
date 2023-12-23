package com.project.undefined.acceptance.job;

import static com.project.undefined.acceptance.utils.ApiDocumentUtils.getDocumentRequest;
import static com.project.undefined.acceptance.utils.ApiDocumentUtils.getDocumentResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.DataUtils;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.common.dto.response.ErrorResponse;
import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.job.dto.request.CreateStageRequest;
import com.project.undefined.job.dto.request.UpdateStageRequest;
import com.project.undefined.job.dto.response.StageResponse;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.entity.State;
import com.project.undefined.job.repository.JobRepository;
import com.project.undefined.job.repository.StageRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

@DisplayName("Stage API 인수 테스트")
public class StageTest extends AcceptanceTest {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private JobRepository jobRepository;

    @Nested
    class create {

        @Test
        @DisplayName("Stage를 생성한다.")
        void create_created() {
            // given
            final Long jobId = DataUtils.findAnyId(jobRepository, Job::getId);
            final CreateStageRequest request = new CreateStageRequest(jobId, "test");

            // when
            final ExtractableResponse<Response> response = given(spec).log().all()
                .filter(document("stage/create",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                        fieldWithPath("jobId").type(JsonFieldType.NUMBER).description("job 아이디"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                    )
                ))
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/stages")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            final Long stageId = RestAssuredUtils.parseLocationId(response, "/stages/");
            assertThat(stageRepository.existsById(stageId)).isTrue();
        }

        @Test
        @DisplayName("name이 공백이면 400 상태를 반환한다.")
        void create_blankName_badRequest() {
            // given
            final Long jobId = DataUtils.findAnyId(jobRepository, Job::getId);
            final CreateStageRequest request = new CreateStageRequest(jobId, "");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/stages")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("name 은(는) 필수 입력 값이며 공백을 제외한 문자를 하나 이상 포함해야 합니다.");
        }

        @Test
        @DisplayName("jobId가 null이면 400 상태를 반환한다.")
        void create_nullJobId_badRequest() {
            // given
            final CreateStageRequest request = new CreateStageRequest(null, "test");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/stages")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("jobId 은(는) 필수 입력 값입니다.");
        }
    }

    @Nested
    class get {

        @Test
        @DisplayName("Stage 상세 정보를 조회한다.")
        void get_ok() {
            // given
            final Long stageId = DataUtils.findAnyId(stageRepository, Stage::getId);

            // when
            final ExtractableResponse<Response> response = given(spec).log().all()
                .filter(document("stage/get",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                        parameterWithName("id").description("아이디")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("state").type(JsonFieldType.STRING).description("상태")
                    )
                ))
                .when()
                .get("/stages/{id}", stageId)
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
            assertThat(stage.getMessage()).isEqualTo(ErrorCode.NON_MATCH_STAGE.getMessage());
        }
    }

    @Nested
    class updateState {

        @Test
        @DisplayName("Stage 상태 값을 갱신한다.")
        void updateState_ok() {
            // given
            final Long stageId = DataUtils.findAnyId(stageRepository, Stage::getId);
            final UpdateStageRequest request = new UpdateStageRequest(State.PASS.toString());

            // when
            final ExtractableResponse<Response> response = given(spec).log().all()
                .filter(document("stage/updateState",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                      parameterWithName("id").description("아이디")
                    ),
                    requestFields(
                        fieldWithPath("state").type(JsonFieldType.STRING).description("상태")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("state").type(JsonFieldType.STRING).description("상태")
                    )
                ))
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .patch("/stages/{id}", stageId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

            final StageResponse stage = RestAssuredUtils.extract(response, StageResponse.class);
            assertThat(stage.getId()).isEqualTo(stageId);
            assertThat(stage.getState().toString()).isEqualTo(request.getState());
        }

        @Test
        @DisplayName("유효하지 않은 id로 Stage를 갱신하면 400 상태를 반환한다.")
        void updateState_invalidStageId_badRequest() {
            // given
            final long notExistStageId = 1_000_000;
            final UpdateStageRequest request = new UpdateStageRequest(State.PASS.toString());

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .patch("/stages/" + notExistStageId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(ErrorCode.NON_MATCH_STAGE.getMessage());
        }

        @Test
        @DisplayName("잘못된 State 값을 넘기면 400 상태를 반환한다.")
        void updateState_invalidState_badRequest() {
            // given
            final Long stageId = DataUtils.findAnyId(stageRepository, Stage::getId);
            final UpdateStageRequest request = new UpdateStageRequest("invalidState");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .patch("/stages/" + stageId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("state 필드에 유효한 값을 입력해야 합니다.");
        }
    }
}
