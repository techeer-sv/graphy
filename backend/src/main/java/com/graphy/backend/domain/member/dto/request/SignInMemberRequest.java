package com.graphy.backend.domain.member.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SignInMemberRequest {
    @Email @NotBlank
    private String email;

    @NotBlank(message = "password cannot be blank")
    private String password;
}
