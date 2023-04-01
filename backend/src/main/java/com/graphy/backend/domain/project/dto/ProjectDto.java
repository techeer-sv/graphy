package com.graphy.backend.domain.project.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    public static class CreateProjectResponse {
        private Long projectId;
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
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetProjectResponse {
        private Long id;
        private String projectName;
        private String description;
        private String thumbNail;

        private LocalDateTime createdAt;
        private List<String> techTags;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetProjectDetailResponse {
        private Long id;
        private String projectName;
        private String content;
        private String description;
        private String thumbNail;

        private LocalDateTime createdAt;
        private List<String> techTags;
    }
}
