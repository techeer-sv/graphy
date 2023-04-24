package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.domain.QComment;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.QProject;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.graphy.backend.domain.comment.domain.QComment.comment;
import static com.graphy.backend.domain.comment.dto.CommentDto.*;
import static com.graphy.backend.domain.project.domain.QProject.project;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    QComment comment = QComment.comment;
    QProject project = QProject.project;

    @Override
    public List<Comment> findCommentsWithMasking(Long projectId) {
        return jpaQueryFactory.select(
                        Projections.fields(
                                Comment.class,
                                comment.id,
                                comment.project,
                                comment.parent,
//                                comment.childList,
                                new CaseBuilder()
                                        .when(comment.isDeleted.eq(true))
                                        .then("삭제된 댓글입니다.")
                                        .otherwise(comment.content).as("content")
                        )
                )
                .from(comment)
                .join(comment.project, project)
                .where(comment.project.id.eq(projectId))
                .fetch();
    }

    public List<Comment> test(Long id) {
        return jpaQueryFactory.select(comment).from(comment).where(comment.project.id.eq(id)).fetch();
    }
}
