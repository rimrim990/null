package com.project.undefined.acceptance.company;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.company.dto.request.CreateCompanyRequest;
import com.project.undefined.company.entity.Company.Region;
import com.project.undefined.company.entity.Company.Series;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Company API 인수 테스트")
public class CompanyTest extends AcceptanceTest {

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
