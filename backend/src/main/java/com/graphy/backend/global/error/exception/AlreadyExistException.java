package com.graphy.backend.global.error.exception;

import com.graphy.backend.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public class AlreadyExistException extends DataIntegrityViolationException {
    private final ErrorCode errorCode;

    public AlreadyExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
