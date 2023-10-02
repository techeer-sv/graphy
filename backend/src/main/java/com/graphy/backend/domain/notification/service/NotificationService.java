package com.graphy.backend.domain.notification.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.notification.domain.Notification;
import com.graphy.backend.domain.notification.dto.NotificationDto;
import com.graphy.backend.domain.notification.repository.NotificationRepository;
import com.graphy.backend.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

import static com.graphy.backend.global.error.ErrorCode.SEND_EMAIL_FAIL;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberService memberService;
    private final MailingService mailingService;

    @Transactional
    public void addNotification(NotificationDto dto, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        dto.setMember(member);
        Notification entity = notificationRepository.save(dto.toEntity());
        try {
            mailingService.sendNotificationEmail(entity);
        } catch (MessagingException e) {
            throw new BusinessException(SEND_EMAIL_FAIL);
        }
    }
}
