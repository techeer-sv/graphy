package com.graphy.backend.domain.auth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTokenInfoResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}