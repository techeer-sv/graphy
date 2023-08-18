package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TagService {

    private final TagRepository tagRepository;

    public Tag findTagByTech(String tech) {
        return tagRepository.findTagByTech(tech);
    }
}
