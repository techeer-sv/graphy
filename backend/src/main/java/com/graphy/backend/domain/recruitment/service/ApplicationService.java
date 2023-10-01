package com.graphy.backend.domain.recruitment.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.notification.domain.NotificationType;
import com.graphy.backend.domain.notification.dto.NotificationDto;
import com.graphy.backend.domain.notification.service.NotificationService;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.service.TagService;
import com.graphy.backend.domain.recruitment.domain.Application;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import com.graphy.backend.domain.recruitment.dto.request.CreateApplicationRequest;
import com.graphy.backend.domain.recruitment.dto.request.TechLevelDto;
import com.graphy.backend.domain.recruitment.dto.response.GetApplicationDetailResponse;
import com.graphy.backend.domain.recruitment.repository.ApplicationRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RecruitmentService recruitmentService;
    private final NotificationService notificationService;
    private final TagService tagService;

    /**
     *TODO
     * @Transactional 적용
     * 중복 지원 방지 로직 추가
     */
    public void addApplication(CreateApplicationRequest request, Member loginUser) {
        Recruitment recruitment = recruitmentService.getRecruitmentById(request.getRecruitmentId());

        Application application = request.toEntity(recruitment, loginUser);

        if (request.getTechLevels() != null) {
            request.getTechLevels().forEach(techLevel -> {
                        Tag tag = tagService.findTagByTech(techLevel.getTech());
                        application.addTag(tag, techLevel.getLevel());
                    });
        }
        applicationRepository.save(application);

        NotificationType notificationType = NotificationType.RECRUITMENT;
        notificationType.setMessage(loginUser.getNickname(), "요청했습니다.");
        NotificationDto notificationDto = NotificationDto.builder()
                .type(notificationType)
                .content(notificationType.getMessage())
                .build();

        notificationService.addNotification(notificationDto, recruitment.getMember().getId());
    }

    public GetApplicationDetailResponse findApplicationById(Long applicationId) {
        Application application = applicationRepository.findApplicationWithFetch(applicationId)
                .orElseThrow(
                () -> new EmptyResultException(ErrorCode.APPLICATION_NOT_EXIST)
        );
        List<TechLevelDto> techLevels = application.getApplicationTags().getValue().stream()
                .map(applicationTag -> TechLevelDto.builder()
                        .tech(applicationTag.getTag().getTech())
                        .level(applicationTag.getLevel())
                        .build())
                .collect(Collectors.toList());

        return GetApplicationDetailResponse.of(application, techLevels);
    }
}
