package com.graphy.backend.domain.project.entity;

import com.graphy.backend.domain.comment.entity.Comment;
import com.graphy.backend.domain.projectimage.entity.ProjectImage;
import com.graphy.backend.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Project")
@Entity
@Builder
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectImage> image = new ArrayList<>();


}
