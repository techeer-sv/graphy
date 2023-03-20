package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.project.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public Tag findTagByTech(String tech);
}

