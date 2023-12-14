package com.project.undefined.acceptance.company;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.entity.Company;
import com.project.undefined.company.entity.Company.Region;
import com.project.undefined.company.entity.Company.Series;
import com.project.undefined.company.repository.CompanyRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("Company API 인수 테스트")
public class CompanyTest extends AcceptanceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("등록된 모든 Company 목록을 조회한다")
    void getAll_ok() {
        // given
        final long count = companyRepository.count();

        // when
        final ExtractableResponse<Response> response = given().log().all()
            .when()
            .get("/companies/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<Company> companies = RestAssuredUtils.extractAsList(response, Company.class);
        assertThat(companies).hasSize((int) count);
    }

    @Test
    @DisplayName("Company 생성에 성공하면 201 상태를 반환한다.")
    void create_created() {
        // given
        final CreateCompanyRequest request = new CreateCompanyRequest("test", Series.B, Region.SEOUL);

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
        final String location = response.header("Location");
        final String companyId = location.split("/companies/")[1];
        assertThat(companyId).isNotBlank();
    }
}
