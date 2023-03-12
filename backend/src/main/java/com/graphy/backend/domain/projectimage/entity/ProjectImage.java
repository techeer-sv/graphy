package com.graphy.backend.domain.projectimage.entity;

import com.graphy.backend.domain.comment.entity.Comment;
import com.graphy.backend.domain.project.entity.Project;
import com.graphy.backend.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ProjectImage")
@Entity
@Builder
public class ProjectImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_image_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ElementCollection @Column(name = "image_url")
    private List<String> url = new ArrayList<>();
}