package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.service.LikeService;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Tag(name = "LikeController", description = "좋아요 관련 API")
@RestController
@RequestMapping("api/v1/likes")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "findLikedMember", description = "좋아요 누른 유저 조회")
    @GetMapping("/{projectId}")
    public ResponseEntity<ResultResponse> findLikedMember(@PathVariable Long projectId) {
        likeService.findLikedMember(projectId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PROJECT_GET_SUCCESS, result));
    }

    @Operation(summary = "likeProject", description = "좋아요 기능")
    @PutMapping("/{projectId}")
    public ResponseEntity<ResultResponse> likeProject(@PathVariable Long projectId, @RequestParam Long memberId) {
        likeService.likeProject(projectId, memberId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.LIKE_PROJECT_SUCCESS));
    }

}
