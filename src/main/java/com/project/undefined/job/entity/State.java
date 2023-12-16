package com.project.undefined.job.entity;

public enum State {
    NONE, PASS, FAIL, WAIT;

    public static State from(final String str) {
        return State.valueOf(str.toUpperCase());
    }
}
