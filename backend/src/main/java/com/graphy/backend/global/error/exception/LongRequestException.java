package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class LongRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public LongRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
