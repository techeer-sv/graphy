package com.graphy.backend.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    RECRUITMENT("님이 팀 합류를 "), // 팀원 모집 글 관련 알림 (지원 신청, 지원 신청 수락)
    FOLLOW("님이 팔로우하였습니다."),          // 팔로우 관련 알림(본인을 팔로우하는 사용자가 발생 시)
    MESSAGE("님이 쪽지를 보냈습니다.");                                       // 쪽지 알림 (쪽지 수신 시)

    private String message;

    public void setMessage(String username, String extraMessage) {
        this.message = username + message +extraMessage;
    }
}
