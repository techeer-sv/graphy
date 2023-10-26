package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.dto.request.CreateProjectRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectPlanRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectsRequest;
import com.graphy.backend.domain.project.dto.request.UpdateProjectRequest;
import com.graphy.backend.domain.project.dto.response.*;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.common.dto.PageRequest;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Tag(name = "ProjectController", description = "프로젝트 관련 API")
@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "createProject", description = "프로젝트 생성")
    @PostMapping
    public ResponseEntity<ResultResponse> projectAdd(@Validated @RequestBody CreateProjectRequest dto, @CurrentUser Member loginUser) {
        CreateProjectResponse response = projectService.addProject(dto, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.PROJECT_CREATE_SUCCESS, response));
    }

    @Operation(summary = "deleteProject", description = "프로젝트 삭제(soft delete)")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ResultResponse> projectRemove(@PathVariable Long projectId, @CurrentUser Member loginUser) {
        projectService.removeProject(projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResultResponse.of(ResultCode.PROJECT_DELETE_SUCCESS));
    }


    @Operation(summary = "updateProject", description = "프로젝트 수정")
    @PutMapping("/{projectId}")
    public ResponseEntity<ResultResponse> projectModify(@PathVariable Long projectId,
                                                        @RequestBody @Validated UpdateProjectRequest dto, @CurrentUser Member loginUser) {
        UpdateProjectResponse result = projectService.modifyProject(projectId, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResultResponse.of(ResultCode.PROJECT_UPDATE_SUCCESS, result));
    }


    @Operation(summary = "findProjectList", description = "프로젝트 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> projectList(GetProjectsRequest dto, PageRequest pageRequest) {
        Pageable pageable = pageRequest.of();
        List<GetProjectResponse> result = projectService.findProjectList(dto, pageable);
        if (result.isEmpty()) throw new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_PAGING_GET_SUCCESS, result));
    }

    @Operation(summary = "findFollowingProjectList", description = "팔로잉하고 있는 사용자의 프로젝트 조회")
    @GetMapping("/following")
    public ResponseEntity<ResultResponse> projectList(PageRequest pageRequest, @CurrentUser Member loginUser){
        Pageable pageable = pageRequest.of();
        List<GetProjectResponse> result = projectService.findFollowingProjectList(loginUser, pageable);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_PAGING_GET_SUCCESS, result));
    }


    @Operation(summary = "findProject", description = "프로젝트 상세 조회")
    @GetMapping("/{projectId}")
    public ResponseEntity<ResultResponse> projectDetails(@PathVariable Long projectId, HttpServletRequest request) {
        GetProjectDetailResponse result = projectService.findProjectById(projectId);
        Cookie cookie = projectService.addViewCount(request, projectId);
        ResponseCookie responseCookie = ResponseCookie.from(cookie.getName(), cookie.getValue())
                .path(cookie.getPath())
                .maxAge(cookie.getMaxAge())
                .build();

        return ResponseEntity.ok()
                .header(SET_COOKIE, responseCookie.toString())
                .body(ResultResponse.of(ResultCode.PROJECT_GET_SUCCESS, result));
    }

    @Operation(summary = "findProjectRank", description = "프로젝트 랭킹 조회")
    @GetMapping("/rank")
    public ResponseEntity<ResultResponse> projectRankList() {
        List<GetProjectRankingResponse> result = projectService.findProjectRank();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_GET_SUCCESS, result));
    }

    @Operation(summary = "getProjectPlan", description = "프로젝트 고도화 계획 제안")
    @PostMapping("/plans")
    public ResponseEntity<ResultResponse> projectPlanDetails(final @RequestBody GetProjectPlanRequest getPlanRequest, @CurrentUser Member loginUser) throws ExecutionException, InterruptedException {
        String prompt = projectService.getPrompt(getPlanRequest);
        projectService.checkGptRequestToken(prompt);

        CompletableFuture<String> futureResult =
                projectService.getProjectPlanAsync(prompt).thenApply(result -> result);
        String response = futureResult.get();

        return ResponseEntity.ok(ResultResponse.of(ResultCode.PLAN_CREATE_SUCCESS, response));
    }
}