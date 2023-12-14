package com.project.undefined.common.exception;

import com.project.undefined.common.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleCompanyException(final BaseException exception) {
        final ErrorResponse response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException() {
        final ErrorResponse response = ErrorResponse.from("유효한 입력이 아닙니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(response);
    }
}
