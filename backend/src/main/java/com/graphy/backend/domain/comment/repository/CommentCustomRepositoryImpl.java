package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.QComment;
import com.graphy.backend.domain.comment.dto.response.GetCommentWithMaskingResponse;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;
import com.graphy.backend.domain.comment.dto.response.QGetCommentWithMaskingResponse;
import com.graphy.backend.domain.comment.dto.response.QGetReplyListResponse;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.graphy.backend.domain.comment.domain.QComment.comment;
import static com.graphy.backend.domain.member.domain.QMember.member;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GetCommentWithMaskingResponse> findCommentsWithMasking(Long id) {
        QComment child = new QComment("child");

        return jpaQueryFactory
                .select(new QGetCommentWithMaskingResponse(
                        new CaseBuilder()
                                .when(comment.isDeleted.isTrue()).then("삭제된 댓글입니다.")
                                .otherwise(comment.content),
                        comment.id,
                        comment.createdAt,
                        member.nickname,
                        jpaQueryFactory.select(child.count())
                                .from(child)
                                .where(child.parent.id.eq(comment.id))
                ))
                .from(comment)
                .leftJoin(comment.childList, child)
                .join(comment.member, member)
                .where(comment.parent.isNull(), comment.project.id.eq(id))
                .groupBy(comment.id)
                .fetch();
    }

    @Override
    public List<GetReplyListResponse> findReplyList(Long parentId) {
        return jpaQueryFactory
                .select(new QGetReplyListResponse(
                        member.nickname,
                        comment.content,
                        comment.id,
                        comment.createdAt
                ))
                .from(comment)
                .join(comment.member, member)
                .where(comment.parent.id.eq(parentId))
                .orderBy(comment.createdAt.asc())
                .fetch();
    }
}
