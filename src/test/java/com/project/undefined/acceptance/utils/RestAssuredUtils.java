package com.project.undefined.acceptance.utils;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class RestAssuredUtils {

    public static <T> List<T> extractAsList(final ExtractableResponse<Response> response, final Class<T> type) {
        return response.body()
            .jsonPath()
            .getList(".", type);
    }

    public static <T> T extract(final ExtractableResponse<Response> response, final Class<T> type) {
        return response.body()
            .as(type);
    }

    private  RestAssuredUtils() {
    }
}
