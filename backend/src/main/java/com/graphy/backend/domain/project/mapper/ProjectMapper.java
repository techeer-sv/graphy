package com.graphy.backend.domain.project.mapper;

import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.entity.Project;
import org.springframework.stereotype.Component;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Component
public class ProjectMapper {
    public UpdateProjectResponse toDto(Project project) {
        return UpdateProjectResponse.builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .content(project.getContent())
                .description(project.getDescription()).build();
    }

    public CreateProjectResponse toDto(Long id) {
        return CreateProjectResponse.builder().projectId(id).build();
    }

    public Project toEntity(CreateProjectRequest dto) {
        return Project.builder()
                .projectName(dto.getProjectName())
                .content(dto.getContent())
                .description(dto.getDescription())
                .build();
    }

}
