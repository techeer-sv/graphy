package com.graphy.backend.domain.comment.domain;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@SQLDelete(sql = "UPDATE comment SET is_deleted = true WHERE comment_id = ?")
@Where(clause = "is_deleted = false")
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

    public void deleteComment(Comment comment) {
        comment.content = "삭제된 댓글입니다.";
    }
}
