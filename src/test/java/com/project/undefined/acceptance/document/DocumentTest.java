package com.project.undefined.acceptance.document;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.DataUtils;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.common.dto.response.ErrorResponse;
import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.document.dto.request.CreateDocumentRequest;
import com.project.undefined.document.dto.response.DocumentResponse;
import com.project.undefined.document.entity.Document;
import com.project.undefined.document.repository.DocumentRepository;
import com.project.undefined.job.entity.Job;
import com.project.undefined.job.repository.JobRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("Document API 인수 테스트")
public class DocumentTest extends AcceptanceTest {


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Nested
    class create {

        @Test
        @DisplayName("Document를 생성한다.")
        void create_created() {
            // given
            final Long jobId = DataUtils.findAnyId(jobRepository, Job::getId);
            final CreateDocumentRequest request = new CreateDocumentRequest(jobId, "test");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/documents")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            final Long documentId = RestAssuredUtils.parseLocationId(response, "/documents/");
            assertThat(documentRepository.existsById(documentId)).isTrue();
        }

        @Test
        @DisplayName("jobId가 null이면 400 상태를 반환한다.")
        void create_nullJobId_badRequest() {
            // given
            final CreateDocumentRequest request = new CreateDocumentRequest(null, "test");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/documents")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("jobId 은(는) 필수 입력 값입니다.");
        }

        @Test
        @DisplayName("jobId가 유효하지 않으면 400 상태를 반환한다.")
        void create_invalidJobId_badRequest() {
            // given
            final long invalidJobId = 1_000_000;
            final CreateDocumentRequest request = new CreateDocumentRequest(invalidJobId, "test");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/documents")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(ErrorCode.NON_MATCH_JOB.getMessage());
        }

        @Test
        @DisplayName("content가 빈 문자열이면 400 상태를 반환한다.")
        void create_blankContent_badRequest() {
            // given
            final Long jobId = DataUtils.findAnyId(jobRepository, Job::getId);
            final CreateDocumentRequest request = new CreateDocumentRequest(jobId, "");

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .body(request)
                .contentType(ContentType.JSON)
                .post("/documents")
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo("content 은(는) 필수 입력 값이며 공백을 제외한 문자를 하나 이상 포함해야 합니다.");
        }
    }

    @Nested
    class get {

        @Test
        @DisplayName("id로 Document 상세 정보를 조회한다.")
        void get_ok() {
            // given
            final Long documentId = DataUtils.findAnyId(documentRepository, Document::getId);

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/documents/" + documentId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            final DocumentResponse document = RestAssuredUtils.extract(response, DocumentResponse.class);
            assertThat(document.getId()).isEqualTo(documentId);
        }

        @Test
        @DisplayName("id가 유효하지 않으면 400 상태를 반환한다.")
        void get_invalidId_badRequest() {
            // given
            final long invalidDocumentId = 1_000_000;

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/documents/" + invalidDocumentId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(ErrorCode.NON_MATCH_DOCUMENT.getMessage());
        }
    }

    @Nested
    class getRelated {

        @Test
        @DisplayName("jobId와 연관된 Document를 조회한다.")
        void getRelated_ok() {
            // given
            final Document document = DataUtils.findAny(documentRepository);
            final Long jobId = document.getJobId();

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/documents/related/" + jobId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            final DocumentResponse result = RestAssuredUtils.extract(response, DocumentResponse.class);
            assertThat(result.getId()).isEqualTo(document.getId());
        }

        @Test
        @DisplayName("jobId와 연관된 Document가 없으면 빈 결과를 반환한다")
        void getRelated_emptyDocument_ok() {
            // given
            final Long jobId = selectNotRelatedJobId();

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/documents/related/" + jobId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.response().asString()).isEqualTo("{}");
        }

        @Test
        @DisplayName("jobId가 유효하지 않으면 400 상태를 반환한다.")
        void getRelated_invalidJobId_badRequest() {
            // given
            final long jobId = 1_000_000L;

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/documents/related/" + jobId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(ErrorCode.NON_MATCH_JOB.getMessage());
        }
    }

    private Long selectNotRelatedJobId() {
        List<Long> relatedJobIds = documentRepository.findAll()
            .stream()
            .map(Document::getJobId)
            .filter(Objects::nonNull)
            .toList();

        return jobRepository.findAll()
            .stream()
            .map(Job::getId)
            .filter(jobId -> !relatedJobIds.contains(jobId))
            .findAny()
            .get();
    }
}
