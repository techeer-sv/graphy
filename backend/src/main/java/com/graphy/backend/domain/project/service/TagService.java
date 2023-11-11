package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TagService {

    private final TagRepository tagRepository;

    public Tags findTagListByName(List<String> techStacks) {
        List<Tag> foundTags = techStacks.stream()
                .map(this::findTagByTech)
                .collect(Collectors.toList());
        return new Tags(foundTags);
    }

    public Tag findTagByTech(String tech) {
        return tagRepository.findTagByTech(tech);
    }
}
