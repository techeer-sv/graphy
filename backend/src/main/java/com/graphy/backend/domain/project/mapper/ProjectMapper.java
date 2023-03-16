package com.graphy.backend.domain.project.mapper;

import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Component
public class ProjectMapper {

    public GetProjectResponse toDto(Project project) {
        return GetProjectResponse.builder().id(project.getId()).projectName(project.getProjectName())
                .description(project.getDescription()).createdAt(project.getCreatedAt()).build();
    }
    public UpdateProjectResponse toUpdateProjectDto(Project project) {
        return UpdateProjectResponse.builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .content(project.getContent())
                .description(project.getDescription()).build();
    }

    public Page<GetProjectResponse> toDtoList(Page<Project> projects) {
        return projects.map(this::toDto);
    }
}
