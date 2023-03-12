package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.comment.entity.Comment;
import com.graphy.backend.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
