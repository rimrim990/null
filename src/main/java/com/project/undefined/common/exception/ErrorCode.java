package com.project.undefined.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NON_MATCH_COMPANY(HttpStatus.BAD_REQUEST, "일치하는 Company가 존재하지 않습니다."),
    NON_MATCH_JOB(HttpStatus.BAD_REQUEST, "일치하는 Job이 존재하지 않습니다."),
    NON_MATCH_STAGE(HttpStatus.BAD_REQUEST, "일치하는 Stage가 존재하지 않습니다."),
    NON_MATCH_RETROSPECT(HttpStatus.BAD_REQUEST, "일치하는 Retrospect가 존재하지 않습니다."),
    NON_MATCH_DOCUMENT(HttpStatus.BAD_REQUEST, "일치하는 Document가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
