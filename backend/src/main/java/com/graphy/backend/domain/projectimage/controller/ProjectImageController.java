package com.graphy.backend.domain.projectimage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ProjectImageController", description = "프로젝트 이미지 관련 API")
@RestController
@RequestMapping("api/v1/project_images")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectImageController {
    @GetMapping
    public String test() {
        return "Hello world!";
    }
}
