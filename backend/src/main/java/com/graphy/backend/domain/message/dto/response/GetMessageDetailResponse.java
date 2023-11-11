package com.graphy.backend.domain.message.dto.response;

import com.graphy.backend.domain.message.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageDetailResponse {
    private Long senderId;
    private String memberNickname;
    private String content;
    private LocalDateTime sentAt;

    public static GetMessageDetailResponse from(Message message) {
        return GetMessageDetailResponse.builder()
                .senderId(message.getSender().getId())
                .memberNickname(message.getSender().getNickname())
                .content(message.getContent())
                .sentAt(message.getCreatedAt())
                .build();
    }
}
