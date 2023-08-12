package com.graphy.backend.domain.project.dto;

import com.graphy.backend.domain.member.dto.MemberDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;


public class ProjectDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class CreateProjectRequest {


        @NotBlank(message = "project name cannot be blank")
        private String projectName;

        @NotBlank(message = "content cannot be blank")
        private String content;

        @NotBlank(message = "description cannot be blank")
        private String description;
        private List<String> techTags;
        private String thumbNail;

    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class GetProjectsRequest {

        private String projectName;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class UpdateProjectRequest {
        @NotBlank(message = "project name cannot be blank")
        private String projectName;
        @NotBlank(message = "content cannot be blank")
        private String content;
        @NotBlank(message = "description cannot be blank")
        private String description;
        private List<String> techTags;
        private String thumbNail;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetPlanRequest {
        @NotBlank(message = "topic cannot be blank")
        private String topic;

        @Size(min=1, max=5, message = "features are not the right number.")
        private List<String> features;

        @Size(min=1, max=15, message = "techStacks are not the right number.")
        private List<String> techStacks;

        @Size(min=1, max=5, message = "plans are not the right number.")
        private List<String> plans;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateProjectResponse {
        private Long projectId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateProjectResponse {
        private Long projectId;
        private String projectName;
        private String content;
        private String description;
        private List<String> techTags;
        private String thumbNail;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetProjectResponse {
        private Long id;
        private MemberDto.GetMemberResponse member;
        private String projectName;
        private String description;
        private String thumbNail;

        private LocalDateTime createdAt;
        private List<String> techTags;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetProjectDetailResponse {
        private Long id;
        private String projectName;
        private String content;
        private String description;
        private String thumbNail;
        private List<GetCommentWithMaskingResponse> commentsList;
        private LocalDateTime createdAt;
        private List<String> techTags;

        private MemberDto.GetMemberResponse member;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetProjectInfoResponse {
        private Long id;
        private String projectName;
        private String description;
    }
}
