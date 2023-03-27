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

    public List<GetProjectResponse> getProjectByName(String projectName, Pageable pageable) {

        Page<Project> projects = projectRepository.findByProjectNameContaining(projectName, pageable);
        return mapper.toDtoList(projects).getContent();
    }

    public List<GetProjectResponse> getProjectByContent(String content, Pageable pageable) {
        Page<Project> projects = projectRepository.findByContentContaining(content, pageable);
        return mapper.toDtoList(projects).getContent();
    }

    public List<GetProjectResponse> getProjects(Pageable pageable) {

        Page<Project> projects = projectRepository.findAll(pageable);
        return mapper.toDtoList(projects).getContent();
    }

    public GetProjectDetailResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        return mapper.toGetProjectDetailDto(project);
    }

    public Tags getTagsWithName(List<String> techStacks) {
        List<Tag> foundTags =  techStacks.stream().map(tagRepository::findTagByTech)
                .collect(Collectors.toList());
        return new Tags(foundTags);
    }
}
