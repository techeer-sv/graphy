package com.graphy.backend.domain.project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateProjectRequest {
        private String projectName;
        private String content;
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
        private String projectName;
        private String content;
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
