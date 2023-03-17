package com.graphy.backend.domain.project.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ProjectDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class UpdateProjectRequest {
        private String projectName;
        private String content;
        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateProjectResponse {
        private Long projectId;
        private String projectName;
        private String content;
        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetProjectResponse {
        private Long id;
        private String projectName;
        private String description;
        private LocalDateTime createdAt;
    }
}
