package com.graphy.backend.domain.member.dto.request;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SignUpMemberRequest {
    @Email @NotBlank
    private String email;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "nickname cannot be blank")
    private String nickname;
    private String introduction;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .introduction(introduction)
                .role(Role.ROLE_USER)
                .build();
    }
}