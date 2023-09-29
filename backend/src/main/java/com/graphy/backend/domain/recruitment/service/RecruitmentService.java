package com.graphy.backend.domain.recruitment.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import com.graphy.backend.domain.recruitment.dto.request.CreateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.dto.response.CreateRecruitmentResponse;
import com.graphy.backend.domain.recruitment.dto.response.GetRecruitmentDetailResponse;
import com.graphy.backend.domain.recruitment.dto.response.GetRecruitmentResponse;
import com.graphy.backend.domain.recruitment.dto.response.UpdateRecruitmentResponse;
import com.graphy.backend.domain.recruitment.repository.RecruitmentRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.error.exception.InvalidMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final ProjectService projectService;
    private final RecruitmentTagService recruitmentTagService;

    @Transactional
    public CreateRecruitmentResponse addRecruitment(CreateRecruitmentRequest request, Member loginUser) {
        Recruitment recruitment = request.toEntity(loginUser);

        if (request.getTechTags() != null) {
            Tags foundTags = projectService.findTagListByName(request.getTechTags());
            recruitment.addTag(foundTags);
        }
        Recruitment entity = recruitmentRepository.save(recruitment);
        return CreateRecruitmentResponse.from(entity.getId());
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
    public UpdateRecruitmentResponse modifyRecruitment(Long recruitmentId, UpdateRecruitmentRequest request, Member loginUser) {
        Recruitment recruitment = recruitmentRepository.findRecruitmentWithMember(recruitmentId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.RECRUITMENT_NOT_EXIST)
        );

        if (recruitment.getMember().getId() != loginUser.getId())
            throw new InvalidMemberException(ErrorCode.INVALID_MEMBER);

        recruitmentTagService.removeProjectTag(recruitment.getId());
        Tags tags = projectService.findTagListByName(request.getTechTags());
        recruitment.updateRecruitment(request, tags);

        return UpdateRecruitmentResponse.from(recruitment);
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
}
