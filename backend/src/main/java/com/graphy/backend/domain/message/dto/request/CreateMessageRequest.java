package com.graphy.backend.domain.message.dto.request;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.message.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageRequest {
    @NotBlank(message = "content cannot be blank")
    private String content;

    @NotNull(message = "recruitmentId cannot be null")
    private Long toMemberId;

    public Message toEntity(Member fromMember, Member toMember) {
        return Message.builder()
                .sender(fromMember)
                .receiver(toMember)
                .content(content)
                .build();
    }
}
