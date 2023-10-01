package com.graphy.backend.domain.message.controller;

import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.message.dto.request.CreateMessageRequest;
import com.graphy.backend.domain.message.dto.response.GetMessageDetailResponse;
import com.graphy.backend.domain.message.service.MessageService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MessageController", description = "쪽지 관련 API")
@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageController {
    private final MessageService messageService;
    @Operation(summary = "createMessage", description = "쪽지 보내기")
    @PostMapping
    public ResponseEntity<ResultResponse> messageAdd(@Validated @RequestBody CreateMessageRequest request,
                                                     @CurrentUser Member loginUser) {
        messageService.addMessage(request, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.MESSAGE_CREATE_SUCCESS));
    }

    @Operation(summary = "findMessage", description = "쪽지 상세 조회")
    @GetMapping("/{messageId}")
    public ResponseEntity<ResultResponse> messageDetails(@PathVariable Long messageId) {
        GetMessageDetailResponse result = messageService.findMessageById(messageId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MESSAGE_GET_SUCCESS, result));
    }
}
