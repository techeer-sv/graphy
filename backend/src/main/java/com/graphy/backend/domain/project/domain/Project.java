package com.graphy.backend.domain.project.domain;

import com.graphy.backend.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Project")
@Entity
@Builder
@SQLDelete(sql = "UPDATE project SET is_deleted = true WHERE project_id = ?")
@Where(clause = "is_deleted = false")
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

    @Embedded
    private ProjectTags projectTags;

    private String thumbNail;

    public void updateProject(String projectName, String content,
                              String description, Tags tags,
                              String thumbNail) {
        this.projectName = projectName;
        this.content = content;
        this.description = description;
        this.thumbNail = thumbNail;
        projectTags.clear();
        addTag(tags);
    }

    public void addTag(Tags tags) {
        projectTags.add(this, tags);
    }

    public List<String> getTagNames() {
        return this.projectTags.getTagNames();
    }

    public List<Long> getTagIds() {
        return this.projectTags.getTagIds();
    }
}
