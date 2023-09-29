package com.graphy.backend.domain.recruitment.domain;

import com.graphy.backend.domain.project.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public RecruitmentTag(Recruitment recruitment, Tag tag) {
        this.recruitment = recruitment;
        this.tag = tag;
    }
}
