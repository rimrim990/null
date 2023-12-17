package com.project.undefined.acceptance.retrospect;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.DataUtils;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.common.dto.response.ErrorResponse;
import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.retrospect.dto.response.RetrospectResponse;
import com.project.undefined.retrospect.entity.Retrospect;
import com.project.undefined.retrospect.repository.RetrospectRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("Retrospect API 인수 테스트")
public class RetrospectTest extends AcceptanceTest {

    @Autowired
    private RetrospectRepository retrospectRepository;

    @Nested
    class get {

        @Test
        @DisplayName("Retrospect 상세 정보를 조회한다.")
        void get_ok() {
            // given
            final Long retrospectId = DataUtils.findAnyId(retrospectRepository, Retrospect::getId);

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/retrospects/" + retrospectId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            final RetrospectResponse retrospect = RestAssuredUtils.extract(response, RetrospectResponse.class);
            assertThat(retrospect.getId()).isEqualTo(retrospectId);
        }

        @Test
        @DisplayName("id가 유효하지 않으면 400 상태를 반환한다.")
        void get_invalidRetrospectId_badRequest() {
            // given
            final long invalidRetrospectId = 1_000_000;

            // when
            final ExtractableResponse<Response> response = given().log().all()
                .when()
                .get("/retrospects/" + invalidRetrospectId)
                .then().log().all()
                .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
            assertThat(error.getMessage()).isEqualTo(ErrorCode.NON_MATCH_RETROSPECT.getMessage());
        }
    }
}
