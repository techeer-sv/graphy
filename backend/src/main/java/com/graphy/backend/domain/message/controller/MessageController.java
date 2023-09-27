package com.graphy.backend.domain.message.controller;

import com.graphy.backend.domain.message.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MessageController", description = "쪽지 관련 API")
@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageController {
    private final MessageService messageService;

}
