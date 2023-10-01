package com.graphy.backend.domain.notification.dto;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.notification.domain.Notification;
import com.graphy.backend.domain.notification.domain.NotificationType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private NotificationType type;
    private Member member;
    private String content;
    private boolean isRead = false;
    private boolean isEmailSent = false;

    public void setMember(Member member) {
        this.member = member;
    }

    public Notification toEntity() {
        return Notification.builder()
                .type(type)
                .member(member)
                .content(content)
                .isRead(this.isRead)
                .isEmailSent(this.isEmailSent)
                .build();
    }
}

