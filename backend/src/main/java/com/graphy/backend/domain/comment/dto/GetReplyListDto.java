package com.graphy.backend.domain.comment.dto;

import java.time.LocalDateTime;

public interface GetReplyListDto {
     String getContent();
     Long getCommentId();
     LocalDateTime getCreatedAt();
}
