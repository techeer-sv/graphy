package com.graphy.backend.domain.follow.controller;

import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.domain.follow.service.FollowService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FollowController", description = "팔로우 API")
@RestController
@RequestMapping("api/v1/follow")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "follow", description = "팔로우 걸기")
    @PostMapping("/{id}")
    public ResponseEntity<ResultResponse> follow(@PathVariable Long id) {
        followService.follow(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOWING_CREATE_SUCCESS));
    }

    @Operation(summary = "unfollow", description = "언팔로우")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> unfollow(@PathVariable Long id) {
        followService.unfollow(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOW_DELETE_SUCCESS));
    }

    @Operation(summary = "Get Follower", description = "팔로워 조회")
    @GetMapping("/follower")
    public ResponseEntity<ResultResponse> getFollower() {
        List<GetMemberListResponse> result = followService.getFollowers();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOWER_GET_SUCCESS, result));
    }

    @Operation(summary = "Get Following", description = "팔로잉 조회")
    @GetMapping("/following")
    public ResponseEntity<ResultResponse> getFollowing() {
        List<GetMemberListResponse> result = followService.getFollowings();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOWER_GET_SUCCESS, result));
    }
}
