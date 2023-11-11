package com.graphy.backend.domain.recruitment.repository.custom;

import com.graphy.backend.domain.recruitment.domain.Application;
import com.graphy.backend.domain.recruitment.repository.ApplicationCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.graphy.backend.domain.member.domain.QMember.member;
import static com.graphy.backend.domain.project.domain.QTag.tag;
import static com.graphy.backend.domain.recruitment.domain.QApplication.application;
import static com.graphy.backend.domain.recruitment.domain.QApplicationTag.applicationTag;

@RequiredArgsConstructor
public class ApplicationCustomRepositoryImpl implements ApplicationCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Application> findApplicationWithFetch(Long applicationId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(application)
                .where(application.id.eq(applicationId))
                .join(application.member, member).fetchJoin()
                .leftJoin(application.applicationTags.value, applicationTag).fetchJoin()
                .leftJoin(applicationTag.tag, tag).fetchJoin()
                .fetchOne());
    }
}
