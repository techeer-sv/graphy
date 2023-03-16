package com.graphy.backend.domain.project.controller;


import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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



    /** A-3) [DELETE] /api/v1/projects/{project_id} 프로젝트 삭제(soft delete) */
    @Operation(summary = "deleteProject", description = "프로젝트 삭제(soft delete)")
    @DeleteMapping("/{project_id}")
    public ResponseEntity<ResultResponse> deleteProject(@PathVariable Long project_id) {
        projectService.deleteProject(project_id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_DELETE_SUCCESS));
    }

    /** A-4) [PUT] /api/v1/projects/{project_id} 프로젝트 수정(변경감지) */
    @Operation(summary = "updateProject", description = "프로젝트 수정(변경감지)")
    @PutMapping("/{project_id}")
    public ResponseEntity<ResultResponse> updateProject(@PathVariable Long project_id,
                                                        @RequestBody UpdateProjectRequest dto) {
        UpdateProjectResponse result = projectService.updateProject(project_id, dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_UPDATE_SUCCESS, result));
    }

    /** A-5) [GET] /api/v1/projects?title={}&page={} 프로젝트 공유글 제목 검색*/
    @Operation(summary = "findProjects", description = "제목으로 프로젝트 검색")
    @GetMapping("/page/{page}")
    public ResponseEntity<ResultResponse> getProjectByName(@PathVariable int page,
                                                           GetProjectByNameRequest dto) {
        PageRequest pageRequest = PageRequest.of(page, dto.getSize(), Sort.by("createdAt").descending());
        List<GetProjectResponse> result = projectService.getProjectByName(dto.getProjectName(), pageRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_GET_SUCCESS, result));
    }
}
