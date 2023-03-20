package com.graphy.backend.domain.project.entity;

import com.graphy.backend.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE project_tag SET is_deleted = true WHERE project_tag_id = ?") // Delete 쿼리가 발생하면 해당 쿼리가 실행
@Where(clause = "is_deleted = false")
public class ProjectTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_tag_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;


    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
