package com.graphy.backend.domain.comment.domain;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder

/*
 * TODO
 * Hard Delete 하기 위해서 제거해야 될 거 같아요
 */
@SQLDelete(sql = "UPDATE comment SET is_deleted = true WHERE comment_id = ?")
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> childList;

    public void updateContent(String content) {
        this.content = content;
    }
}
