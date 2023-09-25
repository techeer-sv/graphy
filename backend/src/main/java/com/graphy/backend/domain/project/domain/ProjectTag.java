package com.graphy.backend.domain.project.domain;

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
public class ProjectTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_tag_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    /**
     * tag는 FetchType.LAZY 필요 없나요??
     */
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public ProjectTag(Project project, Tag tag) {
        this.project = project;
        this.tag = tag;
    }
}
