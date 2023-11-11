package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidTokenException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
