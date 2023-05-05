package com.graphy.backend.domain.comment.dto;


import java.time.LocalDateTime;

public interface CommentWithMaskingDto {
    String getContent();
    Long getCommentId();
    Integer getChildCount();

    LocalDateTime getCreatedAt();
}