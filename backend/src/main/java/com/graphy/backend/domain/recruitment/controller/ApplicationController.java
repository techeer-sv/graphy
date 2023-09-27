package com.graphy.backend.domain.recruitment.controller;

import com.graphy.backend.domain.recruitment.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ApplicationController", description = "프로젝트 구인 게시글 신청 관련 API")
@RestController
@RequestMapping("api/v1/applications")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationController {
    private final ApplicationService applicationService;
}
