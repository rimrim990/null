package com.project.undefined.acceptance.company;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.common.dto.response.ErrorResponse;
import com.project.undefined.common.exception.ErrorCode;
import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.entity.Company;
import com.project.undefined.company.repository.CompanyRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@DisplayName("Company API 인수 테스트")
public class CompanyTest extends AcceptanceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("등록된 모든 Company 목록을 조회한다")
    void getAll_ok() {
        // given
        final List<Long> companyIds = getCompanyIds(Pageable.unpaged());

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/companies/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<Long> extractedIds = RestAssuredUtils.extractAsList(response, Company.class)
            .stream()
            .map(Company::getId)
            .toList();
        assertThat(extractedIds).containsExactlyElementsOf(companyIds);
    }

    @Test
    @DisplayName("유효하지 않은 id로 Company를 조회하면 400 상태를 반환한다.")
    void get_invalidId_badRequest() {
        // given
        long invalidCompanyId = 10_000_000L;

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/companies/" + invalidCompanyId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo(ErrorCode.NON_MATCH_COMPANY.getMessage());
    }

    @Test
    @DisplayName("id로 Company 상세 정보를 조회한다.")
    void get_ok() {
        // given
        Long companyId = getCompanyIds(Pageable.ofSize(1)).get(0);

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/companies/" + companyId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final Company company = RestAssuredUtils.extract(response, Company.class);
        assertThat(company.getId()).isEqualTo(companyId);
    }

    @Test
    @DisplayName("Company 생성에 성공하면 201 상태를 반환한다.")
    void create_created() {
        // given
        final CreateCompanyRequest request = new CreateCompanyRequest("test", "B", "SEOUL");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/companies/" )
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        final Long companyId = RestAssuredUtils.parseLocationId(response, "/companies/");
        assertThat(companyRepository.existsById(companyId)).isTrue();
    }

    @Test
    @DisplayName("잘못된 Series 값을 넘기면 400 상태를 반환한다")
    void create_invalidSeries_badRequest() throws JsonProcessingException {
        // given
        final HashMap<String, Object> request = new HashMap<>();
        request.put("name", "test");
        request.put("series", "invalid");
        request.put("region", "SEOUL");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(objectMapper.writeValueAsString(request))
            .contentType(ContentType.JSON)
            .post("/companies/" )
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("series 필드에 유효한 값을 입력해야 합니다.");
    }

    @Test
    @DisplayName("잘못된 Region 값을 넘기면 400 상태를 반환한다")
    void create_invalidRegion_badRequest() throws JsonProcessingException {
        // given
        final HashMap<String, Object> request = new HashMap<>();
        request.put("name", "test");
        request.put("series", "A");
        request.put("region", "invalid");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(objectMapper.writeValueAsString(request))
            .contentType(ContentType.JSON)
            .post("/companies/" )
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("region 필드에 유효한 값을 입력해야 합니다.");
    }

    @Test
    @DisplayName("name이 공백이면 400 상태를 반환한다")
    void create_blankName_statue400() {
        // given
        final HashMap<String, Object> request = new HashMap<>();
        request.put("name", "");
        request.put("series", "A");
        request.put("region", "Seoul");

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/companies/" )
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ErrorResponse error = RestAssuredUtils.extract(response, ErrorResponse.class);
        assertThat(error.getMessage()).isEqualTo("name 은(는) 필수 입력 값이며 공백을 제외한 문자를 하나 이상 포함해야 합니다.");
    }

    private List<Long> getCompanyIds(final Pageable pageable) {
        final Page<Company> companies = companyRepository.findAll(pageable);
        return companies.getContent()
            .stream()
            .map(Company::getId)
            .toList();
    }
}
