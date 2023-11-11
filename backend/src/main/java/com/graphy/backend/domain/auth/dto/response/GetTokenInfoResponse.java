package com.graphy.backend.domain.auth.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenInfoResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}