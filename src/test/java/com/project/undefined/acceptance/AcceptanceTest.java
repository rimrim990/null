package com.project.undefined.acceptance;

import com.project.undefined.TestContext;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@TestContext
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(scripts = {"classpath:/testdb/data-h2.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = {"classpath:/testdb/clear-h2.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public abstract class AcceptanceTest {

    @Value("${apiPrefix}")
    private String apiPrefix;

    @LocalServerPort
    private int port;

    protected RequestSpecification spec;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        RestAssured.basePath = apiPrefix;
        RestAssured.port = port;

        spec = new RequestSpecBuilder()
            .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
            .build();
    }
}
