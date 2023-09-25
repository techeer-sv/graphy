package com.graphy.backend.domain.recruitment.domain;

import com.graphy.backend.domain.project.domain.Tags;
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
public class ApplicationTags {
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ApplicationTag> value;

    public ApplicationTags() {
        this.value = new ArrayList<>();
    }

    public void clear() {
        value.clear();
    }

    public List<String> getTagNames() {
        return value.stream()
                .map(applicationTag -> applicationTag.getTag().getTech())
                .collect(Collectors.toList());
    }

    public void add(Application application, Tags tags) {
        tags.getTags()
                .forEach(tag -> value.add(new ApplicationTag(application, tag)));
    }


    public List<Long> getTagIds() {
        return value.stream()
                .map(applicationTag -> applicationTag.getTag().getId())
                .collect(Collectors.toList());
    }
}
