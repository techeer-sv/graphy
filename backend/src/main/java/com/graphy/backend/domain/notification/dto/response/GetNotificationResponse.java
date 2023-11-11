package com.graphy.backend.domain.notification.dto.response;

import com.graphy.backend.domain.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNotificationResponse {
    private Long id;
    private String type;
    private String content;
    private boolean isRead;

    public static GetNotificationResponse from(Notification notification) {
        return GetNotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType().toString())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .build();
    }

    public static List<GetNotificationResponse> from(List<Notification> notifications) {
        return notifications.stream()
                .map(GetNotificationResponse::from)
                .collect(Collectors.toList());
    }
}
