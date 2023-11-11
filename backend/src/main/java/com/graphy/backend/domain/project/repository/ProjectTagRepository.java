package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.project.domain.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {


    @Transactional
    public void deleteAllByProjectId(Long id);

    public List<ProjectTag> findAllByProjectId(Long projectId);
}
