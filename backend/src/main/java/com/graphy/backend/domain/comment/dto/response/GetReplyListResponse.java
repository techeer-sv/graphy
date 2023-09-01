package com.graphy.backend.domain.comment.dto.response;

import com.graphy.backend.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetReplyListResponse {
    private String nickname;
    private String content;
    private Long commentId;
    private LocalDateTime createdAt;

    public static GetReplyListResponse from(Comment comment) {
        return GetReplyListResponse.builder()
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}