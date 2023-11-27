package com.graphy.backend.domain.project.repository.custom;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectCustomRepository;
import com.graphy.backend.global.util.QueryDslUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.graphy.backend.domain.follow.domain.QFollow.follow;
import static com.graphy.backend.domain.project.domain.QProject.project;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class ProjectCustomRepositoryImpl implements ProjectCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Project> searchProjectsWith(Pageable pageable, String projectName, String content) {
        List<OrderSpecifier> orders = QueryDslUtil.getAllOrderSpecifiers(pageable, project.getMetadata().getName());
        List<Project> fetch = jpaQueryFactory
                .selectFrom(project).where(projectNameLike(projectName), contentLike(content))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .fetch();

        JPQLQuery<Project> count = jpaQueryFactory
                .select(project)
                .from(project)
                .where(projectNameLike(projectName), contentLike(content));

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }

    @Override
    public Page<Project> findFollowingProjects(Pageable pageable, Long fromId) {
        List<Project> fetch = jpaQueryFactory.selectFrom(project)
                .where(project.member.id.in(
                        select(follow.toId).from(follow).where(follow.fromId.eq(fromId)
                )))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> count = jpaQueryFactory
                .select(project.count())
                .from(project)
                .where(project.member.id.in(
                        select(follow.toId).from(follow).where(follow.fromId.eq(fromId)
                        )));
        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

    private BooleanExpression projectNameLike(String projectName) {
        return projectName != null ? project.projectName.contains(projectName) : null;
    }

    private BooleanExpression contentLike(String content) {
        return content != null ? project.content.contains(content) : null;
    }
}