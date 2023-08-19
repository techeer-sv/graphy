package com.graphy.backend.domain.project.dto.response;

import com.graphy.backend.domain.project.domain.Project;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectInfoResponse {

    private Long id;

    private String projectName;

    private String description;

    public static GetProjectInfoResponse from(Project project) {
        return GetProjectInfoResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .build();
    }
}