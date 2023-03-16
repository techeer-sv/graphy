package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.comment.entity.Comment;
import com.graphy.backend.domain.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByProjectNameContaining(String project_name, PageRequest pageRequest);
}
