package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {
    Page<Project> searchProjectsWith(Pageable pageable, String projectName, String content);

    List<Project> findByMemberId(Long id);
}
