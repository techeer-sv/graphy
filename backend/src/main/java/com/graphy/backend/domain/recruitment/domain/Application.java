package com.graphy.backend.domain.recruitment.domain;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String github;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Embedded
    private ApplicationTags applicationTags;

    public void addTag(Tag tag, Integer level) {
        applicationTags.add(this, tag, level);
    }

    public List<String> getTagNames() {
        return this.applicationTags.getTagNames();
    }

    public List<Long> getTagIds() {
        return this.applicationTags.getTagIds();
    }
}
