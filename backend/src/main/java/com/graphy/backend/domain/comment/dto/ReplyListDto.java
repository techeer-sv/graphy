package com.graphy.backend.domain.comment.dto;

import java.time.LocalDateTime;

public interface ReplyListDto {
     String getNickname();
     String getContent();
     Long getCommentId();
     LocalDateTime getCreatedAt();
}
