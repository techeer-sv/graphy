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
public class RecruitmentTags {
    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL)
    private List<RecruitmentTag> value;

    public RecruitmentTags() {
        this.value = new ArrayList<>();
    }

    public void clear() {
        value.clear();
    }

    public List<String> getTagNames() {
        return value.stream()
                .map(recruitmentTag -> recruitmentTag.getTag().getTech())
                .collect(Collectors.toList());
    }

    public void add(Recruitment recruitment, Tags tags) {
        tags.getTags()
                .forEach(tag -> value.add(new RecruitmentTag(recruitment, tag)));
    }

    public List<Long> getTagIds() {
        return value.stream()
                .map(recruitmentTag -> recruitmentTag.getTag().getId())
                .collect(Collectors.toList());
    }
}
