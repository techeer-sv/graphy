package com.graphy.backend.global.error;

import com.graphy.backend.global.error.exception.AlreadyExistException;
import com.graphy.backend.global.error.exception.BusinessException;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.error.exception.LongRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler({
            BusinessException.class,
    })
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

    @ExceptionHandler(LongRequestException.class)
    protected ResponseEntity<ErrorResponse> handleLongRequestException(LongRequestException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = makeErrorResponse(errorCode);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(AlreadyExistException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = makeErrorResponse(errorCode);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(e.getMessage());
        return handleExceptionInternal(e, e.getBindingResult(),request);
    }

    private ResponseEntity<Object> handleExceptionInternal(
            Exception e, BindingResult bindingResult, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = makeErrorResponse(bindingResult);
        return super.handleExceptionInternal(e, errorResponse, HttpHeaders.EMPTY, ErrorCode.INPUT_INVALID_VALUE.getStatus(), request);
    }


    private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
        return ErrorResponse.builder()
                        .message(ErrorCode.INPUT_INVALID_VALUE.getMessage())
                        .code(ErrorCode.INPUT_INVALID_VALUE.getErrorCode())
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
