package com.graphy.backend.domain.comment.dto;


public interface GetCommentWithMaskingDto {
    String getContent();
    Long getCommentId();
    Integer getChildCount();
}