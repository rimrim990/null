package com.project.undefined.common.exception;

import com.project.undefined.common.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
