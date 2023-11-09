package com.graphy.backend.domain.recruitment.controller;

import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.dto.request.CreateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.dto.response.GetApplicationResponse;
import com.graphy.backend.domain.recruitment.dto.response.GetRecruitmentDetailResponse;
import com.graphy.backend.domain.recruitment.dto.response.GetRecruitmentResponse;
import com.graphy.backend.domain.recruitment.service.RecruitmentService;
import com.graphy.backend.global.common.dto.PageRequest;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "RecruitmentController", description = "프로젝트 구인 게시글 관련 API")
@RestController
@RequestMapping("api/v1/recruitments")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @Operation(summary = "createRecruitment", description = "구인 게시글 생성")
    @PostMapping
    public ResponseEntity<ResultResponse> recruitmentAdd(@Validated @RequestBody CreateRecruitmentRequest request,
                                                         @CurrentUser Member loginUser) {
        recruitmentService.addRecruitment(request, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.RECRUITMENT_CREATE_SUCCESS));
    }

    @Operation(summary = "findRecruitment", description = "구인 게시글 상세 조회")
    @GetMapping("/{recruitmentId}")
    public ResponseEntity<ResultResponse> recruitmentDetails(@PathVariable Long recruitmentId) {
        GetRecruitmentDetailResponse result = recruitmentService.findRecruitmentById(recruitmentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RECRUITMENT_GET_SUCCESS, result));
    }

    /**
     * 모집 여부 필터링
     * 모집 중 필터링
     */
    @Operation(summary = "findRecruitmentList", description = "구인 게시글 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> recruitmentList(@RequestParam(required = false) List<Position> positions,
                                                          @RequestParam(required = false) List<String> tags,
                                                          @RequestParam(required = false) String keyword,
                                                          PageRequest pageRequest) {
        Pageable pageable = pageRequest.of();
        List<GetRecruitmentResponse> result = recruitmentService.findRecruitmentList(positions, tags, keyword, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RECRUITMENT_PAGING_GET_SUCCESS, result));
    }

    @Operation(summary = "findApplicationList", description = "프로젝트 참가 신청서 목록 조회")
    @GetMapping("{recruitmentId}/applications")
    public ResponseEntity<ResultResponse> applicationList(@PathVariable Long recruitmentId,
                                                          PageRequest pageRequest) {
        Pageable pageable = pageRequest.of();
        List<GetApplicationResponse> result = recruitmentService.findApplicationList(recruitmentId, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.APPLICATION_PAGING_GET_SUCCESS, result));
    }

    @Operation(summary = "updateRecruitment", description = "구인 게시글 수정")
    @PutMapping("/{recruitmentId}")
    public ResponseEntity<ResultResponse> RecruitmentModify(@PathVariable Long recruitmentId,
                                                            @RequestBody @Validated UpdateRecruitmentRequest request,
                                                            @CurrentUser Member loginUser) {
        recruitmentService.modifyRecruitment(recruitmentId, request, loginUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResultResponse.of(ResultCode.RECRUITMENT_UPDATE_SUCCESS));
    }

    @Operation(summary = "deleteRecruitment", description = "구인 게시글 삭제(soft delete)")
    @DeleteMapping("/{recruitmentId}")
    public ResponseEntity<ResultResponse> recruitmentRemove(@PathVariable Long recruitmentId,
                                                            @CurrentUser Member loginUser) {
        recruitmentService.removeRecruitment(recruitmentId, loginUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResultResponse.of(ResultCode.RECRUITMENT_DELETE_SUCCESS));
    }
}


