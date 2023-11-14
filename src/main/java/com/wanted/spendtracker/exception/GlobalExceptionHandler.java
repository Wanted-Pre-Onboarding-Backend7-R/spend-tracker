package com.wanted.spendtracker.exception;

import com.wanted.spendtracker.dto.response.CustomErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.wanted.spendtracker.exception.ErrorCode.*;
import static org.springframework.boot.web.servlet.server.Encoding.DEFAULT_CHARSET;
import static org.springframework.http.HttpHeaders.CONTENT_ENCODING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int FIRST_ERROR_INDEX = 0;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e) {
        logError(e);
        ErrorCode errorCode = e.getErrorCode();
        return createResponseEntity(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logError(e);
        String firstErrorMessage = getFirstErrorMessage(e);
        ErrorCode errorCode = errorMessageToErrorCode(firstErrorMessage, COMMON_INVALID_PARAMETER);
        return createResponseEntity(errorCode);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logError(e);
        return createResponseEntity(BAD_REQUEST, null, e.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<CustomErrorResponse> handleNumberFormatException(NumberFormatException e) {
        logError(e);
        return createResponseEntity(BAD_REQUEST, null, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationException(AuthenticationException e) {
        logError(e);
        if (e.getCause() instanceof CustomException customException) {
            return createResponseEntity(customException.getErrorCode());
        }
        return createResponseEntity(AUTH_AUTHENTICATION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e) {
        logError(e);
        if (e.getCause() instanceof CustomException customException) {
            return createResponseEntity(customException.getErrorCode());
        }
        return createResponseEntity(INTERNAL_SERVER_ERROR, COMMON_SERVER_ERROR.name(), e.getMessage());
    }

    private ErrorCode errorMessageToErrorCode(String errorMessage, ErrorCode defaultErrorCode) {
        try {
            return ErrorCode.valueOf(errorMessage);
        } catch (IllegalArgumentException | NullPointerException ex) {
            return defaultErrorCode;
        }

    }

    private String getFirstErrorMessage(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return bindingResult
                .getAllErrors()
                //첫 번째 에러만 반환
                .get(FIRST_ERROR_INDEX)
                .getDefaultMessage();
    }

    private ResponseEntity<CustomErrorResponse> createResponseEntity(ErrorCode errorCode) {
        return createResponseEntity(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
    }

    private ResponseEntity<CustomErrorResponse> createResponseEntity(HttpStatus httpStatus, String code, String message) {
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
        return ResponseEntity
                .status(httpStatus)
                .header(CONTENT_ENCODING, DEFAULT_CHARSET.name())
                .contentType(APPLICATION_JSON)
                .body(customErrorResponse);
    }

    private void logError(Exception e) {
        log.error(e.getClass().getSimpleName(), e);
    }

}
