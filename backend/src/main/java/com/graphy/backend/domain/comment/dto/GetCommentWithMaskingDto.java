package com.graphy.backend.domain.comment.dto;


import java.time.LocalDateTime;

public interface GetCommentWithMaskingDto {
    String getContent();
    Long getCommentId();
    Integer getChildCount();

    LocalDateTime getCreatedAt();
}