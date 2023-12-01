package com.project.undefined.acceptance;

import com.project.undefined.TestContext;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@TestContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @Value("${apiPrefix}")
    private String apiPrefix;

    @LocalServerPort
    private int port;

    @BeforeAll
    @Sql({"classpath:/testdb/schema-h2.sql"})
    static void beforeAll() {
    }

    @BeforeEach
    @Sql({"classpath:/testdb/data-h2.sql"})
    void setUp() {
        RestAssured.basePath = apiPrefix;
        RestAssured.port = port;
    }

    @AfterEach
    @Sql({"classpath:/testdb/clear-h2.sql"})
    void tearDown() {
    }
}
