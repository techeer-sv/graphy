package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.repository.ProjectTagRepository;
import com.graphy.backend.domain.project.repository.TagRepository;
import com.graphy.backend.global.chatgpt.dto.GptCompletionDto;
import com.graphy.backend.global.chatgpt.service.GPTChatRestService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CommentRepository commentRepository;

    private final GPTChatRestService gptChatRestService;

    @PostConstruct
    public void initTag() throws IOException {
        if (tagRepository.existsById(1L))
            return;
        ClassPathResource resource = new ClassPathResource("tag.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String s;

        while ((s = br.readLine()) != null) {
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
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST));

        List<CommentWithMaskingDto> comments = commentRepository.findCommentsWithMasking(projectId);

        return mapper.toGetProjectDetailDto(project, comments);
    }

    public Tags getTagsWithName(List<String> techStacks) {
        List<Tag> foundTags = techStacks.stream().map(tagRepository::findTagByTech)
                .collect(Collectors.toList());
        return new Tags(foundTags);
    }

    public List<GetProjectResponse> getProjects(GetProjectsRequest dto, Pageable pageable) {
        Page<Project> projects = projectRepository.searchProjectsWith(pageable, dto.getProjectName(), dto.getContent());
        return mapper.toDtoList(projects).getContent();
    }

    public GptCompletionDto.GptCompletionResponse getProjectPlan(final GetPlanRequest request) {
        GptCompletionDto.GptCompletionRequest dto = new GptCompletionDto.GptCompletionRequest();
        String techStacks = String.join(", ", request.getTechStacks());
        String plans = String.join(", ", request.getPlans());
        String features = String.join(", ", request.getFeatures());
        String prompt = techStacks + "를 이용해" + request.getTopic() +"를 개발 중이고, 현재"
                + features + "까지 기능 구현한 상태에서 고도화된 기능과 " + plans + "을 사용한 고도화 방안을 알려줘";
        dto.setPrompt(prompt);
        return gptChatRestService.completion(dto);
    }
}
