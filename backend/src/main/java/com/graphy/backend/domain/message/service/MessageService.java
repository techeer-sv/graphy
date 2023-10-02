package com.graphy.backend.domain.message.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.message.domain.Message;
import com.graphy.backend.domain.message.dto.request.CreateMessageRequest;
import com.graphy.backend.domain.message.dto.response.GetMessageDetailResponse;
import com.graphy.backend.domain.message.dto.response.GetMessageResponse;
import com.graphy.backend.domain.message.repository.MessageRepository;
import com.graphy.backend.domain.notification.domain.NotificationType;
import com.graphy.backend.domain.notification.dto.NotificationDto;
import com.graphy.backend.domain.notification.service.NotificationService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberService memberService;
    private final NotificationService notificationService;

    @Transactional
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

    public GetMessageDetailResponse findMessageById(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.MESSAGE_NOT_EXIST)
        );
        return GetMessageDetailResponse.from(message);
    }

    public List<GetMessageResponse> findMessageList(Member loginUser, Pageable pageable) {
        List<GetMessageResponse> messages = messageRepository.findMessages(pageable, loginUser.getId());

        if (messages.isEmpty()) throw new EmptyResultException(ErrorCode.MESSAGE_NOT_EXIST);
        else return messages;
    }
}
