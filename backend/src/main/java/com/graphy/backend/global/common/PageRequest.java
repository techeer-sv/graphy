package com.graphy.backend.global.common;

import com.graphy.backend.global.error.exception.BusinessException;
import org.springframework.data.domain.Sort;

import static com.graphy.backend.global.error.ErrorCode.INPUT_INVALID_VALUE;


public class PageRequest {
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;

    private int page = 1;
    private int size = 10;
    private Sort.Direction direction = Sort.Direction.DESC;
    
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public org.springframework.data.domain.PageRequest of() {
        if (size <= 0) {
            throw new BusinessException(INPUT_INVALID_VALUE);
        }
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
    }
}