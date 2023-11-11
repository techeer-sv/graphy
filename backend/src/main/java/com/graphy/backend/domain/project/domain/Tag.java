package com.graphy.backend.domain.project.domain;


import com.graphy.backend.domain.recruitment.domain.RecruitmentTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tech;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private Set<ProjectTag> projects;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private Set<RecruitmentTag> recruitments;
}