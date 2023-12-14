package com.project.undefined.common.exception;

import com.project.undefined.common.dto.response.ErrorResponse;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleCompanyException(final BaseException exception) {
        final ErrorResponse response = ErrorResponse.from(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        final MethodArgumentNotValidException exception) {
        final String errorMessages = resolveErrorMessages(exception.getBindingResult());
        final ErrorResponse response = ErrorResponse.from(errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(response);
    }

    private String resolveErrorMessages(final BindingResult bindingResult) {
        return bindingResult.getAllErrors()
            .stream()
            .map(this::getErrorMessage)
            .collect(Collectors.joining(", "));
    }

    private String getErrorMessage(final ObjectError error) {
        final String[] codes = error.getCodes();
        for (String code : codes) {
            try {
                return messageSource.getMessage(code, error.getArguments(), Locale.KOREA);
            } catch(NoSuchMessageException ignored) {}
        }
        return error.getDefaultMessage();
    }
}
