package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.repository.ProjectTagRepository;
import com.graphy.backend.domain.project.repository.TagRepository;
import com.graphy.backend.domain.project.util.ProjectSpecification;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;

    private final ProjectMapper mapper;


    @PostConstruct
    public void initTag() throws IOException {

        ClassPathResource resource = new ClassPathResource("tag.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        String s;
        while ((s = br.readLine()) != null){
            Tag tag = Tag.builder().tech(s).build();
            tagRepository.save(tag);

        }
        br.close();
    }

    public CreateProjectResponse createProject(CreateProjectRequest dto) {
        Project entity = mapper.toEntity(dto);
        if (dto.getTechTags() != null) {
            Tags foundTags = getTagsWithName(dto.getTechTags());
            entity.addTag(foundTags);
        }


        Project project = projectRepository.save(entity);
        return mapper.toCreateProjectDto(project.getId());
    }

    public void deleteProject(Long project_id) {
        try {
            projectRepository.deleteById(project_id);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST);
        }
    }

    @Transactional
    public UpdateProjectResponse updateProject(Long projectId, UpdateProjectRequest dto) {
        Project project = projectRepository.findById(projectId).get();
        projectTagRepository.deleteAllByProjectId(project.getId()); 
        Tags updatedTags = getTagsWithName(dto.getTechTags());

        project.updateProject(dto.getProjectName(), dto.getContent(), dto.getDescription(), updatedTags, dto.getThumbNail());

        return mapper.toUpdateProjectDto(project);
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

    public List<GetProjectResponse> getProjects(GetProjectsRequest dto, Pageable pageable) {
        if (dto == null) {
            return getProjects(pageable);
        }

        Specification projectSpecification = ProjectSpecification.searchWith(dto.getProjectName(), dto.getContent());
        Page<Project> projects = projectRepository.findAll(projectSpecification, pageable);
        return mapper.toDtoList(projects).getContent();
    }
}
