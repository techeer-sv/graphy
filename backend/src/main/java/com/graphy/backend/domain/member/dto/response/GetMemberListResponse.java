package com.graphy.backend.domain.member.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetMemberListResponse {
    private Long id;
    private String nickname;
}
