package com.graphy.backend.domain.project.dto.response;

import com.graphy.backend.domain.project.domain.Project;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectResponse {

    private Long projectId;

    private String projectName;

    private String content;

    private String description;

    private List<String> techTags;

    private String thumbNail;

    public static UpdateProjectResponse from(Project project) {
        return UpdateProjectResponse.builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .content(project.getContent())
                .thumbNail(project.getThumbNail())
                .description(project.getDescription()).build();
    }
}
