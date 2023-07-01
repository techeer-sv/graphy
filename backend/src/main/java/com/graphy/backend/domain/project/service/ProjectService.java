package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.repository.ProjectTagRepository;
import com.graphy.backend.domain.project.repository.TagRepository;
import com.graphy.backend.global.auth.jwt.CustomUserDetailsService;
import com.graphy.backend.global.chatgpt.dto.GptCompletionDto.GptCompletionRequest;
import com.graphy.backend.global.chatgpt.dto.GptCompletionDto.GptCompletionResponse;
import com.graphy.backend.global.chatgpt.service.GPTChatRestService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.error.exception.LongRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static com.graphy.backend.global.config.ChatGPTConfig.MAX_REQUEST_TOKEN;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;
    private final CustomUserDetailsService customUserDetailsService;

    private final ProjectMapper mapper;
    private final CommentRepository commentRepository;

    private final GPTChatRestService gptChatRestService;

//    @PostConstruct
//    public void initTag() throws IOException {
//        if (tagRepository.existsById(1L))
//            return;
//        ClassPathResource resource = new ClassPathResource("tag.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
//        String s;
//
//        while ((s = br.readLine()) != null) {
//            Tag tag = Tag.builder().tech(s).build();
//            tagRepository.save(tag);
//        }
//        br.close();
//    }

    public CreateProjectResponse createProject(CreateProjectRequest dto) {
        Member loginUser = customUserDetailsService.getLoginUser();
        Project entity = mapper.toEntity(dto,loginUser);
        if (dto.getTechTags() != null) {
            Tags foundTags = getTagsWithName(dto.getTechTags());
            entity.addTag(foundTags);
        }
        Project project = projectRepository.save(entity);
        return mapper.toCreateProjectDto(project.getId());
    }

    public void deleteProject(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
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

    @Async
    public CompletableFuture<String> getProjectPlanAsync(String prompt) {
        GptCompletionRequest dto = new GptCompletionRequest();
        CompletableFuture<String> response = new CompletableFuture<>();

        dto.setPrompt(prompt);
        GptApiCall(dto, response::complete);
        return response;
    }

    private void GptApiCall(GptCompletionRequest request, Consumer<String> callback) {
        System.out.println("비동기 작업 시작");
        GptCompletionResponse result = gptChatRestService.completion(request);
        System.out.println("비동기 작업 완료");
        String response = result.getMessages().get(0).getText()
                .replace("\n", " ").replace("\n\n", " ");
        callback.accept(response);
    }

    public void checkGptRequestToken(String prompt) {
        String[] tokens = prompt.split("\\s+");  // 공백을 기준으로 텍스트를 분할
       if (tokens.length * 10 > MAX_REQUEST_TOKEN)
           throw new LongRequestException(ErrorCode.REQUEST_TOO_MUCH_TOKENS);
    }

    public String getPrompt(final GetPlanRequest request){
        String techStacks = String.join(", ", request.getTechStacks());
        String plans = String.join(", ", request.getPlans());
        String features = String.join(", ", request.getFeatures());
        return techStacks + "를 이용해" + request.getTopic() +"를 개발 중이고, 현재"
                + features + "까지 기능 구현한 상태에서 고도화된 기능과 " + plans + "을 사용한 고도화 방안을 알려줘";
    }
}