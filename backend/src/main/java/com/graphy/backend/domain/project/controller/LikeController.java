package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.domain.project.service.LikeService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "LikeController", description = "좋아요 관련 API")
@RestController
@RequestMapping("api/v1/likes")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "findLikedMember", description = "좋아요 누른 유저 조회")
    @GetMapping("/{projectId}")
    public ResponseEntity<ResultResponse> likedMemberList(@PathVariable Long projectId) {
        List<GetMemberListResponse> likedMemberList = likeService.findLikedMemberList(projectId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.LIKED_MEMBER_GET_SUCCESS, likedMemberList));
    }

    @Operation(summary = "likeProject", description = "좋아요 기능")
    @PutMapping("/{projectId}")
    public ResponseEntity<ResultResponse> likeProject(@PathVariable Long projectId, @CurrentUser Member loginUser) {
        likeService.likeOrUnlikeProject(projectId, loginUser);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.LIKE_PROJECT_SUCCESS));
    }

}
