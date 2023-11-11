package com.graphy.backend.domain.message.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class GetMessageResponse {
    private Long senderId;
    private String memberNickname;
    private String content;
    private LocalDateTime sentAt;

    @QueryProjection
    public GetMessageResponse(Long senderId, String memberNickname, String content, LocalDateTime sentAt) {
        this.senderId = senderId;
        this.memberNickname = memberNickname;
        this.content = content;
        this.sentAt = sentAt;
    }
}
