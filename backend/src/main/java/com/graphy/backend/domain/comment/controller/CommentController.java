package com.graphy.backend.domain.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CommentController", description = "댓글 관련 API")
@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentController {

    @GetMapping
    public String test() {
        return "Hello world!";
    }
}
