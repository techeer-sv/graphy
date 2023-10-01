package com.graphy.backend.domain.recruitment.controller;

import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.recruitment.dto.request.CreateApplicationRequest;
import com.graphy.backend.domain.recruitment.service.ApplicationService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ApplicationController", description = "프로젝트 구인 게시글 신청 관련 API")
@RestController
@RequestMapping("api/v1/applications")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationController {
    private final ApplicationService applicationService;

    @Operation(summary = "createApplication", description = "프로젝트 참가 신청")
    @PostMapping
    public ResponseEntity<ResultResponse> applicationAdd(@Validated @RequestBody CreateApplicationRequest request,
                                                         @CurrentUser Member loginUser) {
        applicationService.addApplication(request, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.APPLICATION_CREATE_SUCCESS));
    }

}
