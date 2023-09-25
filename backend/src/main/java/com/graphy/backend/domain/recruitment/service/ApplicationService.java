package com.graphy.backend.domain.recruitment.service;

import com.graphy.backend.domain.recruitment.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
}
