package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
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
        Tags foundTags = getTagsWithName(dto.getTechTags());
        Project entity = mapper.toEntity(dto);
        entity.addTag(foundTags);

        Project project = projectRepository.save(entity);
        return mapper.toCreateProjectDto(project.getId());
    }

    public void deleteProject(Long project_id) {
        projectRepository.deleteById(project_id);
    }

    public UpdateProjectResponse updateProject(Long projectId, UpdateProjectRequest dto) {
        Project project = projectRepository.findById(projectId).get();
        projectTagRepository.deleteAllByProjectId(project.getId());

        Tags updatedTags = getTagsWithName(dto.getTechTags());

        project.updateProject(dto.getProjectName(), dto.getContent(), dto.getDescription(), updatedTags, dto.getThumbNail());

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
