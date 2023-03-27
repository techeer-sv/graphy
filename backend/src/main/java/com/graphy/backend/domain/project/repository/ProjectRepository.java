package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByProjectNameContaining(String project_name, Pageable pageable);
    Page<Project> findByContentContaining(String content, Pageable pageable);
}
