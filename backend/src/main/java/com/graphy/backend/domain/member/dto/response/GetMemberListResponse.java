package com.graphy.backend.domain.member.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberListResponse {
    private Long id;
    private String nickname;
}
