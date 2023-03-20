package com.graphy.backend.domain.project.entity;

import com.graphy.backend.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Project")
@Entity
@Builder
@SQLDelete(sql = "UPDATE project SET is_deleted = true WHERE project_id = ?") // Delete 쿼리가 발생하면 해당 쿼리가 실행
@Where(clause = "is_deleted = false") // 해당 엔티티를 조회하는 모든 요청에 "deleted = false" 조건이 적용
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "project")
    private List<ProjectTag> projectTags = new ArrayList<>();

    public void updateProject(String projectName, String content, String description) {
        this.projectName = projectName;
        this.content = content;
        this.description = description;
    }
}
