package com.graphy.backend.domain.notification.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.notification.dto.NotificationDto;
import com.graphy.backend.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberService memberService;

    @Transactional
    public void addNotification(NotificationDto dto, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        dto.setMember(member);

        notificationRepository.save(dto.toEntity());
    }
}
