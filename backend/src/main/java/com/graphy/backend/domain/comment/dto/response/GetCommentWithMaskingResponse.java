package com.graphy.backend.domain.comment.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class GetCommentWithMaskingResponse {
    private String content;
    private Long commentId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    private String nickname;
    private Long childCount;

    @QueryProjection
    public GetCommentWithMaskingResponse(String content, Long commentId, LocalDateTime createdAt, String nickname, Long childCount) {
        this.content = content;
        this.commentId = commentId;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.childCount = childCount;
    }
}