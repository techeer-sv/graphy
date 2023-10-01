package com.graphy.backend.domain.recruitment.repository;

import com.graphy.backend.domain.recruitment.domain.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationCustomRepository {
    Page<Application> findAllByRecruitmentId(Long id, Pageable pageable);
}
