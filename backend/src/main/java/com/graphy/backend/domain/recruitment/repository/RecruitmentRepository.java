package com.graphy.backend.domain.recruitment.repository;

import com.graphy.backend.domain.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>  {
}
