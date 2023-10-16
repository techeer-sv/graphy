package com.graphy.backend.domain.notification.controller;

import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.notification.dto.response.GetNotificationResponse;
import com.graphy.backend.domain.notification.service.NotificationService;
import com.graphy.backend.global.common.PageRequest;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "NotificationController", description = "알림 관련 API")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "findNotificationList", description = "알림 목록 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> notificationList(PageRequest pageRequest, @CurrentUser Member loginUser) {
        List<GetNotificationResponse> result = notificationService.findNotificationList(pageRequest, loginUser);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.NOTIFICATION_PAGING_GET_SUCCESS, result));
    }
}
