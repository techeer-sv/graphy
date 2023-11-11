package com.graphy.backend.domain.recruitment.service;

import com.graphy.backend.domain.recruitment.repository.RecruitmentTagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentTagService {
    private final RecruitmentTagRepository recruitmentTagRepository;

    @Transactional
    public void removeProjectTag(Long projectId) {
        recruitmentTagRepository.deleteAllByRecruitmentId(projectId);
    }
}
