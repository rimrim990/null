package com.project.undefined.acceptance;

import com.project.undefined.TestContext;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@TestContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(scripts = {"classpath:/testdb/schema-h2.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(scripts = {"classpath:/testdb/data-h2.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = {"classpath:/testdb/clear-h2.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public abstract class AcceptanceTest {

    @Value("${apiPrefix}")
    private String apiPrefix;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.basePath = apiPrefix;
        RestAssured.port = port;
    }
}
