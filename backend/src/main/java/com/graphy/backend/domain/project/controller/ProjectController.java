package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Tag(name = "ProjectController", description = "프로젝트 관련 API")
@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "createProject", description = "프로젝트 생성")
    @PostMapping
    public ResponseEntity<ResultResponse> createProject(@Validated @RequestBody CreateProjectRequest dto) {

        CreateProjectResponse response = projectService.createProject(dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_CREATE_SUCCESS, response));
    }

    @Operation(summary = "deleteProject", description = "프로젝트 삭제(soft delete)")
    @DeleteMapping("/{project_id}")
    public ResponseEntity<ResultResponse> deleteProject(@PathVariable Long project_id) {
        projectService.deleteProject(project_id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_DELETE_SUCCESS));
    }


    @Operation(summary = "updateProject", description = "프로젝트 수정(변경감지)")
    @PutMapping("/{projectId}")
    public ResponseEntity<ResultResponse> updateProject(@PathVariable Long projectId,
                                                        @RequestBody @Validated UpdateProjectRequest dto) {
        UpdateProjectResponse result = projectService.updateProject(projectId, dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_UPDATE_SUCCESS, result));
    }


    @Operation(summary = "findProjects", description = "프로젝트 조회 \n\n" + "\t⚠️ sort 주의사항 ⚠️\n\n" +
            "\t\t1. sort는 공백(\"\"), id, createdAt, updatedAt, content, description, projectName 중 하나 입력\n\n" +
            "\t\t2. 오름차순이 기본입니다. 내림차순을 원하실 경우 {정렬기준},desc (ex. \"id,desc\")를 입력해주세요 (콤마 사이 띄어쓰기 X)\n\n" +
            "\t\t3. sort의 default(공백 입력) : createdAt(최신순), 내림차순")
    @PostMapping("/search")
    public ResponseEntity<ResultResponse> getProjects(@RequestBody GetProjectsRequest dto,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable page) {

        List<GetProjectResponse> result = projectService.getProjects(dto, page);

        if (result.size() == 0) throw new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_PAGING_GET_SUCCESS, result));
    }

    @Operation(summary = "findProject", description = "프로젝트 상세 조회")
    @GetMapping("/{projectId}")
    public ResponseEntity<ResultResponse> getProject(@PathVariable Long projectId) {
        GetProjectDetailResponse result = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_GET_SUCCESS, result));
    }
}
