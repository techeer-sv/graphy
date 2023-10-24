package com.graphy.backend.domain.recruitment.repository;

import com.graphy.backend.domain.recruitment.domain.Application;

import java.util.Optional;

public interface ApplicationCustomRepository {
    Optional<Application> findApplicationWithFetch(Long applicationId);
}
