package com.graphy.backend.domain.projectimage.repository;

import com.graphy.backend.domain.comment.entity.Comment;
import com.graphy.backend.domain.projectimage.entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
}
