package com.graphy.backend.domain.comment.dto.response;

import com.graphy.backend.domain.comment.domain.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class GetReplyListResponse {
    private String nickname;
    private String content;
    private Long commentId;
    private LocalDateTime createdAt;

    @QueryProjection
    public GetReplyListResponse(String nickname, String content, Long commentId, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.content = content;
        this.commentId = commentId;
        this.createdAt = createdAt;
    }

    public static GetReplyListResponse from(Comment comment) {
        return GetReplyListResponse.builder()
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}