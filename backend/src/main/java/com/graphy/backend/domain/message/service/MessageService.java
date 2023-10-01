package com.graphy.backend.domain.message.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.message.dto.request.CreateMessageRequest;
import com.graphy.backend.domain.message.repository.MessageRepository;
import com.graphy.backend.domain.notification.domain.NotificationType;
import com.graphy.backend.domain.notification.dto.NotificationDto;
import com.graphy.backend.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberService memberService;
    private final NotificationService notificationService;

    public void addMessage(CreateMessageRequest request, Member loginUser) {
        Member receiver = memberService.findMemberById(request.getToMemberId());
        messageRepository.save(request.toEntity(loginUser, receiver));

        NotificationType notificationType = NotificationType.MESSAGE;
        notificationType.setMessage(loginUser.getNickname(), "");
        NotificationDto notificationDto = NotificationDto.builder()
                .type(notificationType)
                .content(notificationType.getMessage())
                .build();

        notificationService.addNotification(notificationDto, receiver.getId());
    }
}
