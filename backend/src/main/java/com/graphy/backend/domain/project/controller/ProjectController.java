package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.project.service.ProjectService;
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
    public ResponseEntity<ResultResponse> createProject(@RequestBody CreateProjectRequest dto) {

        CreateProjectResponse response = projectService.createProject(dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_CREATE_SUCCESS, response));
    }

    /**
     * A-3) [DELETE] /api/v1/projects/{project_id} 프로젝트 삭제(soft delete)
     */

    @Operation(summary = "deleteProject", description = "프로젝트 삭제(soft delete)")
    @DeleteMapping("/{project_id}")
    public ResponseEntity<ResultResponse> deleteProject(@PathVariable Long project_id) {
        projectService.deleteProject(project_id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_DELETE_SUCCESS));
    }


    /**
     * A-4) [PUT] /api/v1/projects/{project_id} 프로젝트 수정(변경감지)
     */

    @Operation(summary = "updateProject", description = "프로젝트 수정(변경감지)")
    @PutMapping("/{projectId}")
    public ResponseEntity<ResultResponse> updateProject(@PathVariable Long project_id,
                                                        @RequestBody UpdateProjectRequest dto) {
        UpdateProjectResponse result = projectService.updateProject(project_id, dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_UPDATE_SUCCESS, result));
    }

    /**
     * A-5) [GET] /api/v1/projects?title={}&page={} 프로젝트 공유글 제목 검색
     */
    @Operation(summary = "findProjects", description = "(default: 전체 조회) 제목으로 프로젝트 검색")
    @GetMapping("")
    public ResponseEntity<ResultResponse> getProjects(@RequestParam(required = false) String projectName, @RequestParam(required = false) String content,
                                                      @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable page) {

        List<GetProjectResponse> result;

        if (projectName != null) {
            result = projectService.getProjectByName(projectName, page);
        } else if (content != null) {
            result = projectService.getProjectByContent(content, page);
        } else {
            result = projectService.getProjects(page);
        }

        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_PAGING_GET_SUCCESS, result));
    }

    @Operation(summary = "findProjects", description = "(default: 전체 조회) 제목으로 프로젝트 검색")
    @GetMapping("{projectId}")
    public ResponseEntity<ResultResponse> getProject(@PathVariable Long projectId) {
        GetProjectDetailResponse result = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_GET_SUCCESS, result));
    }
}
