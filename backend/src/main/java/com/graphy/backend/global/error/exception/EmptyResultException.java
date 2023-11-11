package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;

@Getter
public class EmptyResultException extends EmptyResultDataAccessException {
    private final ErrorCode errorCode;

    public EmptyResultException(ErrorCode errorCode) {
        super(errorCode.getMessage(), 0);
        this.errorCode = errorCode;
    }
}
