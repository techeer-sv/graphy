package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public class AlreadyFollowingException extends DataIntegrityViolationException {
    private final ErrorCode errorCode;

    public AlreadyFollowingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
