package com.graphy.backend.domain.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Embeddable
@AllArgsConstructor
public class ProjectTags {

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectTag> value;

    public ProjectTags() {
        this.value = new ArrayList<>();
    }

    public void clear() {
        value.clear();
    }


    public List<String> getTagNames() {
        return value.stream()
                .map(projectTag -> projectTag.getTag().getTech())
                .collect(Collectors.toList());
    }

    public void add(Project project, Tags tags) {
        tags.getTags()
                .forEach(tag -> value.add(new ProjectTag(project, tag)));
    }


    public List<Long> getTagIds() {
        return value.stream()
                .map(projectTag -> projectTag.getTag().getId())
                .collect(Collectors.toList());
    }
}
