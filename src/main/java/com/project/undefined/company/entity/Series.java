package com.project.undefined.company.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Series {

    A, B, C, D, E, F, IPO;

    @JsonCreator
    public static Series from(final String str) {
        return Series.valueOf(str.toUpperCase());
    }
}
