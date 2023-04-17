package com.graphy.backend.global.error;

import com.graphy.backend.global.error.exception.BusinessException;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = makeErrorResponse(errorCode);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(EmptyResultException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyResultException(EmptyResultException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = makeErrorResponse(errorCode);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(e.getMessage());
        return handleExceptionInternal(e, ErrorCode.INPUT_INVALID_VALUE, e.getBindingResult(),request);
    }


    private ResponseEntity<Object> handleExceptionInternal(
            Exception e, ErrorCode errorCode, WebRequest request) {
        log.error(e.getMessage(), e);
        return super.handleExceptionInternal(e, ErrorResponse.of(errorCode), HttpHeaders.EMPTY, errorCode.getStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternal(
            Exception e, ErrorCode errorCode, BindingResult bindingResult, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = makeErrorResponse(errorCode,bindingResult);
        return super.handleExceptionInternal(e, errorResponse, HttpHeaders.EMPTY, errorCode.getStatus(), request);
    }


    private ErrorResponse makeErrorResponse(ErrorCode errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getErrorCode())
                        .errors(ErrorResponse.FieldError.of(bindingResult))
                        .build();
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getErrorCode())
                .build();
    }
}
