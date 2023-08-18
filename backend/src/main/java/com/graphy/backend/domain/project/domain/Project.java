package com.graphy.backend.domain.project.domain;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String projectName;

    @Lob
    @Column(nullable = true)
    private String content;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt")
    private List<Comment> comments;

    @Column(nullable = true)
    private String description;

    @Embedded
    private ProjectTags projectTags;

    private String thumbNail;

    @Column(nullable = true)
    @ColumnDefault("0")
    private int likeCount = 0;

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

    public void updateLikeCount(int amount) {
        this.likeCount += amount;
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
