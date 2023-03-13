package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.entity.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectService {
    private final ProjectRepository projectRepository;
    public void deleteProject(Long project_id) {projectRepository.deleteById(project_id);}
}
