package com.graphy.backend.domain.member.dto.response;

import com.graphy.backend.domain.member.domain.Member;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetMemberResponse implements Serializable {
    private String nickname;
    private String email;

    public static GetMemberResponse from(Member member) {
        return GetMemberResponse.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}