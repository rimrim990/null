package com.project.undefined.acceptance.retrospect;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.undefined.acceptance.AcceptanceTest;
import com.project.undefined.acceptance.utils.RestAssuredUtils;
import com.project.undefined.job.entity.Stage;
import com.project.undefined.job.repository.StageRepository;
import com.project.undefined.retrospect.dto.request.CreateRetrospectRequest;
import com.project.undefined.retrospect.entity.Retrospect;
import com.project.undefined.retrospect.repository.RetrospectRepository;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        final Long stageId = getStageIds(Pageable.ofSize(1)).get(0);
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

    private List<Long> getStageIds(final Pageable pageable) {
        final Page<Stage> stages = stageRepository.findAll(pageable);
        return stages.getContent()
            .stream()
            .map(Stage::getId)
            .toList();
    }

    private List<Long> getRetrospectIds(final Pageable pageable) {
        final Page<Retrospect> retrospects = retrospectRepository.findAll(pageable);
        return retrospects.getContent()
            .stream()
            .map(Retrospect::getId)
            .toList();
    }
}
