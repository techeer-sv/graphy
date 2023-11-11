package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidMemberException extends BusinessException {
    private final ErrorCode errorCode;

    public InvalidMemberException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
