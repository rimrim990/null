package com.project.undefined.company.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Region {

    SEOUL, ETC;

    @JsonCreator
    public static Region from(final String str) {
        return Region.valueOf(str.toUpperCase());
    }
}
