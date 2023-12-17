package com.project.undefined.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
}
