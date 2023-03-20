package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.entity.Project;
import com.graphy.backend.domain.project.entity.ProjectTag;
import com.graphy.backend.domain.project.entity.Tag;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.repository.ProjectTagRepository;
import com.graphy.backend.domain.project.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;

    private final ProjectMapper mapper;


    public CreateProjectResponse createProject(CreateProjectRequest dto) {
        Project project = mapper.toEntity(dto);
        List<Tag> tags = getTags(dto.getTechTags());

        List<ProjectTag> projectTags = tags.stream()
                .map(t -> projectTagRepository.save(mapper.toEntity(project, t)))
                .collect(Collectors.toList());

        project.setProjectTags(projectTags);

        Project projectEntity = projectRepository.save(project);
        return mapper.toCreateProjectDto(projectEntity.getId());
    }

    public void deleteProject(Long project_id) {
        projectRepository.deleteById(project_id);
    }

    public UpdateProjectResponse updateProject(Long project_id, UpdateProjectRequest dto) {
        Project project = projectRepository.findById(project_id).get();
        project.updateProject(dto.getProjectName(), dto.getContent(), dto.getDescription());
        projectRepository.save(project);
        return mapper.toUpdateProjectDto(project);
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

    public List<Tag> getTags(List<String> techStacks) {
        return techStacks.stream().map(tagRepository::findTagByTech)
                .collect(Collectors.toList());
    }

}
