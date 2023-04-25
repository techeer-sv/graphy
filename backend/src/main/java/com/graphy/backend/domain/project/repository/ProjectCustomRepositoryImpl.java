package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.project.domain.Project;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


import java.util.List;

import static com.graphy.backend.domain.project.domain.QProject.project;
@RequiredArgsConstructor
public class ProjectCustomRepositoryImpl implements ProjectCustomRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Project> searchProjectsWith(Pageable pageable, String projectName, String content) {
        List<Project> fetch = queryFactory
                .selectFrom(project).where(projectNameEq(projectName), contentEq(content))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Project> count = queryFactory
                .select(project)
                .from(project)
                .where(projectNameEq(projectName), contentEq(content));

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }

    private BooleanExpression projectNameEq(String projectName) {
        return projectName != null ? project.projectName.eq(projectName) : null;
    }

    private BooleanExpression contentEq(String content) {
        return content != null ? project.content.eq(content) : null;
    }
}