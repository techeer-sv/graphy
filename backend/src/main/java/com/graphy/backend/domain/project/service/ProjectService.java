package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.entity.Project;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;
    public void deleteProject(Long project_id) {projectRepository.deleteById(project_id);}

    public UpdateProjectResponse updateProject(Long project_id, UpdateProjectRequest dto) {
        Project project = projectRepository.findById(project_id).get();
        project.updateProject(dto.getProjectName(), dto.getContent(), dto.getDescription());
        projectRepository.save(project);
        return mapper.toDto(project);
    }
}
