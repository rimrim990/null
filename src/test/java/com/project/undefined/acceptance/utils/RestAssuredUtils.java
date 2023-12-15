package com.project.undefined.acceptance.utils;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Objects;

public class RestAssuredUtils {

    private static final String LOCATION_HEADER = "Location";

    public static <T> List<T> extractAsList(final ExtractableResponse<Response> response, final Class<T> type) {
        return response.body()
            .jsonPath()
            .getList(".", type);
    }

    public static <T> T extract(final ExtractableResponse<Response> response, final Class<T> type) {
        return response.body()
            .as(type);
    }

    public static Long parseLocationId(final ExtractableResponse<Response> response, final String endPointRegex) {
        final String location = response.header(LOCATION_HEADER);
        Objects.requireNonNull(location, "Location 헤더 값이 없습니다.");

        final String[] split = location.split(endPointRegex);
        if (split.length < 2) throw new IllegalArgumentException("엔드포인트와 일치하는 Id 값이 없습니다.");

        return Long.parseLong(split[1]);
    }

    private  RestAssuredUtils() {
    }
}
