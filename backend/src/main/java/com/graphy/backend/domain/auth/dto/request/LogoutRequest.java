package com.graphy.backend.domain.auth.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {
    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;
}