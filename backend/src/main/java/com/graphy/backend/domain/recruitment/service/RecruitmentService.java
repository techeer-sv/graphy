package com.graphy.backend.domain.recruitment.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.service.TagService;
import com.graphy.backend.domain.recruitment.domain.Application;
import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import com.graphy.backend.domain.recruitment.dto.request.CreateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.dto.response.GetApplicationResponse;
import com.graphy.backend.domain.recruitment.dto.response.GetRecruitmentDetailResponse;
import com.graphy.backend.domain.recruitment.dto.response.GetRecruitmentResponse;
import com.graphy.backend.domain.recruitment.repository.ApplicationRepository;
import com.graphy.backend.domain.recruitment.repository.RecruitmentRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.error.exception.InvalidMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentTagService recruitmentTagService;
    private final ApplicationRepository applicationRepository;
    private final TagService tagService;

    @Transactional
    public void addRecruitment(CreateRecruitmentRequest request, Member loginUser) {
        Recruitment recruitment = request.toEntity(loginUser);
        if (request.getTechTags() != null) {
            Tags foundTags = tagService.findTagListByName(request.getTechTags());
            recruitment.addTag(foundTags);
        }
        recruitmentRepository.save(recruitment);
    }

    @Transactional(readOnly = true)
    public GetRecruitmentDetailResponse findRecruitmentById(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.RECRUITMENT_NOT_EXIST)
        );
        return GetRecruitmentDetailResponse.from(recruitment);
    }

    @Transactional(readOnly = true)
    public List<GetRecruitmentResponse> findRecruitmentList(List<Position> positions,
                                                            List<String> tags,
                                                            String keyword,
                                                            Pageable pageable) {
        List<Recruitment> result = recruitmentRepository.findRecruitments(positions, tags, keyword, pageable);
        if (result.isEmpty()) throw new EmptyResultException(ErrorCode.RECRUITMENT_NOT_EXIST);

        return GetRecruitmentResponse.listOf(result);
    }

    @Transactional
    public void modifyRecruitment(Long recruitmentId, UpdateRecruitmentRequest request, Member loginUser) {
        Recruitment recruitment = recruitmentRepository.findRecruitmentWithMember(recruitmentId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.RECRUITMENT_NOT_EXIST)
        );

        if (recruitment.getMember().getId() != loginUser.getId())
            throw new InvalidMemberException(ErrorCode.INVALID_MEMBER);

        recruitmentTagService.removeProjectTag(recruitment.getId());
        Tags tags = tagService.findTagListByName(request.getTechTags());
        recruitment.updateRecruitment(request, tags);
    }

    @Transactional
    public void removeRecruitment(Long recruitmentId, Member loginUser) {
        Recruitment recruitment = recruitmentRepository.findRecruitmentWithMember(recruitmentId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.RECRUITMENT_NOT_EXIST)
        );

        if (recruitment.getMember().getId() != loginUser.getId())
            throw new InvalidMemberException(ErrorCode.INVALID_MEMBER);

        recruitment.delete();
    }

    @Transactional(readOnly = true)
    public Recruitment getRecruitmentById(Long id) {
        return recruitmentRepository.findById(id).orElseThrow(() -> new EmptyResultException(ErrorCode.RECRUITMENT_NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public List<GetApplicationResponse> findApplicationList(Long recruitmentId, Pageable pageable) {
        Page<Application> applicationList = applicationRepository.findAllByRecruitmentId(recruitmentId, pageable);
        return applicationList.stream()
                .map(GetApplicationResponse::from)
                .collect(Collectors.toList());
    }
}
