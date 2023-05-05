package com.graphy.backend.domain.plan.controller;

import com.graphy.backend.global.chatgpt.dto.service.GPTChatRestService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.graphy.backend.global.chatgpt.dto.GptCompletionDto.GptCompletionRequest;
import static com.graphy.backend.global.chatgpt.dto.GptCompletionDto.GptCompletionResponse;

@Tag(name = "CommentController", description = "고도화 계획 관련 API")
@RestController
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
public class PlanController {

    private final GPTChatRestService gptChatRestService;

    @PostMapping
    public ResponseEntity<ResultResponse> createPlan(final @RequestBody GptCompletionRequest gptCompletionRequest) {
        GptCompletionResponse response = gptChatRestService.completion(gptCompletionRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PLAN_CREATE_SUCCESS, response));
    }
}
