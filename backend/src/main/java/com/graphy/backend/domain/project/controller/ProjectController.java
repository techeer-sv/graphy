package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ResultResponse> createProject(@RequestParam List<MultipartFile> images,
                                                        @RequestBody CreateProjectRequest dto) {

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
    @PutMapping("/{project_id}")
    public ResponseEntity<ResultResponse> updateProject(@PathVariable Long project_id,
                                                        @RequestBody UpdateProjectRequest dto) {
        UpdateProjectResponse result = projectService.updateProject(project_id, dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_UPDATE_SUCCESS, result));
    }
}
