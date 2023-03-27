package com.graphy.backend.domain.project.domain;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Tags {

    private List<Tag> tags;

    public List<Long> getTagIds() {
        return tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
    }

    public List<String> getTagNames() {
        return tags.stream()
                .map(Tag::getTech)
                .collect(Collectors.toList());
    }

    public Tags(List<Tag> tags) {
        this.tags = tags;
    }
}