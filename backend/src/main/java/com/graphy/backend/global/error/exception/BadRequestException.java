package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;

public class BadRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}