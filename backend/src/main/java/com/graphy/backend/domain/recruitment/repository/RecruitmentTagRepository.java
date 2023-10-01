package com.graphy.backend.domain.recruitment.repository;

import com.graphy.backend.domain.recruitment.domain.RecruitmentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RecruitmentTagRepository extends JpaRepository<RecruitmentTag, Long> {
    @Transactional
    public void deleteAllByRecruitmentId(Long id);
}
