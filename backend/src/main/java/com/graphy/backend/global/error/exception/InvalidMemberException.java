package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidMemberException extends RuntimeException{
    private final ErrorCode errorCode;

    public InvalidMemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
