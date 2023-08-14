package com.graphy.backend.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentWithMaskingResponse {
    private String content;
    private Long commentId;
    private LocalDateTime createdAt;
    private String nickname;
    private Long childCount;
}