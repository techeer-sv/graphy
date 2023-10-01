package com.graphy.backend.domain.recruitment.domain;

import com.graphy.backend.domain.project.domain.Tag;
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
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
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

    public void add(Application application, Tag tag, Integer level) {
        value.add(new ApplicationTag(application, tag, level));
    }


    public List<Long> getTagIds() {
        return value.stream()
                .map(applicationTag -> applicationTag.getTag().getId())
                .collect(Collectors.toList());
    }
}