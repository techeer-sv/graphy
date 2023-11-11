package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.repository.ProjectTagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTagService {

    private final ProjectTagRepository projectTagRepository;

    public void removeProjectTag(Long projectId) {
        projectTagRepository.deleteAllByProjectId(projectId);
    }
}
