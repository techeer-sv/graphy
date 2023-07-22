package com.graphy.backend.domain.member.dto;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

public class MemberDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class LoginMemberRequest {
        @Email
        private String email;

        @NotBlank(message = "password cannot be blank")
        private String password;

    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class GetMemberResponse {
        private String nickname;
        private String email;

        public static GetMemberResponse toDto(Member member) {
            return GetMemberResponse.builder()
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
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

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class GetMyPageResponse {
        private String nickname;
        private String introduction;
        private int followerCount;
        private int followingCount;
        private List<ProjectInfo> projectInfoList;

        public static GetMyPageResponse from(Member member, List<ProjectInfo> projectInfoList) {
            return GetMyPageResponse.builder()
                    .nickname(member.getNickname())
                    .introduction(member.getIntroduction())
                    .followerCount(member.getFollowerCount())
                    .followingCount(member.getFollowingCount())
                    .projectInfoList(projectInfoList)
                    .build();
        }
    }
}
