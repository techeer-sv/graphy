package com.graphy.backend.domain.comment.dto;

import java.time.LocalDateTime;

public interface ReplyListDto {
     String getContent();
     Long getCommentId();
     LocalDateTime getCreatedAt();
}
