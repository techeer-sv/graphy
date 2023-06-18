package com.graphy.backend.domain.member.dto;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LoginMemberRequest {
        @Email
        private String email;

        @NotBlank(message = "password cannot be blank")
        private String password;

    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateMemberRequest {
        @Email
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
}
