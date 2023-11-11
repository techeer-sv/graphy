package com.graphy.backend.domain.project.dto.response;

import com.graphy.backend.domain.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectInfoResponse {

    private Long id;

    private String projectName;

    private String content;

    public static GetProjectInfoResponse from(Project project) {
        return GetProjectInfoResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .content(project.getContent())
                .build();
    }
}