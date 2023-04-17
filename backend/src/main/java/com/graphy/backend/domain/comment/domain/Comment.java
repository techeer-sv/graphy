package com.graphy.backend.domain.comment.domain;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childList;

    @Builder
    public Comment(String content, Project project, Comment parent) {
        this.content = content;
        this.project = project;
        this.parent = parent;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
