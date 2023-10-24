package com.graphy.backend.domain.recruitment.repository;

import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecruitmentCustomRepository {
    List<Recruitment> findRecruitments(List<Position> positions,
                                       List<String> tags,
                                       String keyword,
                                       Pageable pageable);

    Optional<Recruitment> findRecruitmentWithMember(Long recruitmentId);
}
