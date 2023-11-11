package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.dto.response.GetCommentWithMaskingResponse;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMyPageResponse;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.dto.request.CreateProjectRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectPlanRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectsRequest;
import com.graphy.backend.domain.project.dto.request.UpdateProjectRequest;
import com.graphy.backend.domain.project.dto.response.*;
import com.graphy.backend.domain.project.repository.ProjectRepository;
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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.graphy.backend.global.config.ChatGPTConfig.MAX_REQUEST_TOKEN;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectService {
    private final ProjectRepository projectRepository;

    private final MemberService memberService;
    private final ProjectTagService projectTagService;
    private final CommentService commentService;
    private final TagService tagService;
    private final GPTChatRestService gptChatRestService;
    private final RedisTemplate<String, Long> redisTemplate;
    private final RedisTemplate<String, GetProjectDetailResponse> redisRankingTemplate;
    private final String RANKING_KEY = "ranking";
    private final String TOP_RANKING_PROJECT_KEY = "topRankingProject";
    private final int START_RANKING = 0;
    private final int END_RANKING = 9;
    private int NEXT_RANKING = END_RANKING;

    @Transactional
    public CreateProjectResponse addProject(CreateProjectRequest dto, Member loginUser) {
        Project entity = dto.toEntity(loginUser);
        if (dto.getTechTags() != null) {
            Tags foundTags = tagService.findTagListByName(dto.getTechTags());
            entity.addTag(foundTags);
        }
        Project project = projectRepository.save(entity);
        return CreateProjectResponse.from(project.getId());
    }

    @Transactional
    public void removeProject(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
            if (isProjectIdExistInRanking(projectId)) addNextRankingProject(projectId);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST);
        }
    }

    @Transactional
    public UpdateProjectResponse modifyProject(Long projectId, UpdateProjectRequest dto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST));
        projectTagService.removeProjectTag(project.getId());
        Tags updatedTags = tagService.findTagListByName(dto.getTechTags());

        project.updateProject(dto, updatedTags);
        if (isProjectIdExistInRanking(projectId)) modifyProjectInRanking(project);

        return UpdateProjectResponse.from(project);
    }

    public GetProjectDetailResponse findProjectById(Long projectId) {
        Project project = this.getProjectById(projectId);
        List<GetCommentWithMaskingResponse> comments = commentService.findCommentListWithMasking(projectId);
        return GetProjectDetailResponse.of(project, comments);
    }

    @Transactional
    public Cookie addViewCount(HttpServletRequest request, Long projectId) {
        Project project = this.getProjectById(projectId);

        Cookie[] cookies = request.getCookies();
        Cookie oldCookie = this.findCookie(cookies, "View_Count");

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + projectId + "]")) {
                oldCookie.setValue(oldCookie.getValue() + "[" + projectId + "]");
                project.addViewCount();
                redisTemplate.opsForZSet().incrementScore(RANKING_KEY, projectId, 1);
            }
            oldCookie.setPath("/");
            return oldCookie;
        } else {
            Cookie newCookie = new Cookie("View_Count", "[" + projectId + "]");
            newCookie.setPath("/");
            project.addViewCount();
            redisTemplate.opsForZSet().incrementScore(RANKING_KEY, projectId, 1);
            return newCookie;
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public List<GetProjectResponse> findProjectList(GetProjectsRequest dto, Pageable pageable) {
        Page<Project> projects = projectRepository.searchProjectsWith(pageable, dto.getProjectName(), dto.getContent());
        return GetProjectResponse.listOf(projects).getContent();
    }

    public List<GetProjectResponse> findFollowingProjectList(Member loginUser, Pageable pageable) {
        Member member = memberService.findMemberById(loginUser.getId());
        Page<Project> projects = projectRepository.findFollowingProjects(pageable, member.getId());
        return GetProjectResponse.listOf(projects).getContent();
    }

    public List<GetProjectInfoResponse> findProjectInfoList(Long id) {
        return projectRepository.findByMemberId(id).stream()
                .map(GetProjectInfoResponse::from)
                .collect(Collectors.toList());
    }

    public GetMyPageResponse myPage(Member member) {
        List<GetProjectInfoResponse> projectInfoList = this.findProjectInfoList(member.getId());
        return GetMyPageResponse.of(member, projectInfoList);
    }

    public GetMyPageResponse myPageByNickname(String nickname) {
        Member member = memberService.findMemberByNickname(nickname);
        List<GetProjectInfoResponse> projectInfoList = this.findProjectInfoList(member.getId());
        return GetMyPageResponse.of(member, projectInfoList);
    }


    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST));
    }

    public void saveProject(Project project) {
        projectRepository.save(project);
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
        GptCompletionResponse result = gptChatRestService.completion(request);
        String response = result.getMessages().get(0).getText()
                .replace("\n", " ").replace("\n\n", " ");
        callback.accept(response);
    }

    public void checkGptRequestToken(String prompt) {
        String[] tokens = prompt.split("\\s+");  // 공백을 기준으로 텍스트를 분할
       if (tokens.length * 10 > MAX_REQUEST_TOKEN)
           throw new LongRequestException(ErrorCode.REQUEST_TOO_MUCH_TOKENS);
    }

    public String getPrompt(final GetProjectPlanRequest request){
        String techStacks = String.join(", ", request.getTechStacks());
        String plans = String.join(", ", request.getPlans());
        String features = String.join(", ", request.getFeatures());
        return techStacks + "를 이용해" + request.getTopic() +"를 개발 중이고, 현재"
                + features + "까지 기능 구현한 상태에서 고도화된 기능과 " + plans + "을 사용한 고도화 방안을 알려줘";
    }

    public List<GetProjectDetailResponse> findTopRankingProjectList() {
        HashOperations<String, Long, GetProjectDetailResponse> hashOperation = redisRankingTemplate.opsForHash();
        List<Long> topRankingProjectIdList = getTopRankingProjectIdList();

        return topRankingProjectIdList.stream()
                .map(projectId -> hashOperation.get(TOP_RANKING_PROJECT_KEY, projectId))
                .collect(Collectors.toList());
    }


    @Scheduled(cron = "0 0 6 ? * MON", zone = "Asia/Seoul")
    public void initializeProjectRanking() {
        NEXT_RANKING = END_RANKING;
        HashOperations<String, Long, GetProjectDetailResponse> hashOperation = redisRankingTemplate.opsForHash();
        List<GetProjectDetailResponse> topRankingProjectList = getRankedProjectListById(getTopRankingProjectIdList());

        topRankingProjectList.forEach(project -> hashOperation.put(TOP_RANKING_PROJECT_KEY, project.getId(), project));
        redisRankingTemplate.expire(TOP_RANKING_PROJECT_KEY, 7, TimeUnit.DAYS);
    }

    private List<Long> getTopRankingProjectIdList() {
        ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();
        return new ArrayList<>(Objects.requireNonNull(zSetOperations.reverseRange(RANKING_KEY, START_RANKING, END_RANKING)));
    }

    private List<GetProjectDetailResponse> getRankedProjectListById(List<Long> projectIds) {
        List<Project> projectList = projectRepository.findAllById(projectIds);

        return projectList.stream()
                .map(project -> {
                    List<GetCommentWithMaskingResponse> comments = commentService.findCommentListWithMasking(project.getId());
                    return GetProjectDetailResponse.of(project, comments);
                })
                .collect(Collectors.toList());
    }

    private void addNextRankingProject(Long deletedProjectId) {
        ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();
        HashOperations<String, Long, GetProjectDetailResponse> hashOperation = redisRankingTemplate.opsForHash();

        hashOperation.delete(TOP_RANKING_PROJECT_KEY, deletedProjectId);
        zSetOperations.remove(RANKING_KEY, deletedProjectId);

        Long nextRankingProjectId = Objects.requireNonNull(zSetOperations.reverseRange(RANKING_KEY, NEXT_RANKING, NEXT_RANKING))
                .iterator()
                .next();
        NEXT_RANKING++;

        GetProjectDetailResponse nextRankingProject = this.findProjectById(nextRankingProjectId);
        hashOperation.put(TOP_RANKING_PROJECT_KEY, nextRankingProjectId, nextRankingProject);
    }

    private void modifyProjectInRanking(Project project) {
        HashOperations<String, Long, GetProjectDetailResponse> hashOperation = redisRankingTemplate.opsForHash();
        List<GetCommentWithMaskingResponse> comments = commentService.findCommentListWithMasking(project.getId());
        GetProjectDetailResponse updateProject = GetProjectDetailResponse.of(project, comments);
        hashOperation.put(TOP_RANKING_PROJECT_KEY, project.getId(), updateProject);
    }

    private boolean isProjectIdExistInRanking(Long projectId) {
        HashOperations<String, Long, GetProjectDetailResponse> hashOperation = redisRankingTemplate.opsForHash();
        return hashOperation.hasKey(TOP_RANKING_PROJECT_KEY, projectId);
    }
}