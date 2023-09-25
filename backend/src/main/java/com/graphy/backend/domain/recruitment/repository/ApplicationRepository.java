package com.graphy.backend.domain.recruitment.repository;

import com.graphy.backend.domain.recruitment.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
