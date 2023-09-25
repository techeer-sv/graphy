package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectCustomRepository {
    Page<Project> searchProjectsWith(Pageable pageable, String projectName, String content);

    Page<Project> findFollowingProjects(Pageable pageable, Long fromId);
}
