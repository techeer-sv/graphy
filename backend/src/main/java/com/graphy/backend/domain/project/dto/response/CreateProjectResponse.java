package com.graphy.backend.domain.project.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectResponse {

    private Long projectId;

    public static CreateProjectResponse from(Long projectId) {
        return CreateProjectResponse.builder()
                .projectId(projectId).build();
    }
}