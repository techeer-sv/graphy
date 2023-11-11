package com.graphy.backend.domain.project.repository.custom;

import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.domain.project.repository.LikeCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.graphy.backend.domain.project.domain.QLike.like;
import static com.graphy.backend.domain.project.domain.QProject.project;

@RequiredArgsConstructor
public class LikeCustomRepositoryImpl implements LikeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GetMemberListResponse> findLikedMembers(Long projectId) {
        return jpaQueryFactory.select(Projections.constructor(GetMemberListResponse.class,
                like.member.id,
                like.member.nickname))
                .from(like)
                .leftJoin(like.project, project)
                .where(project.id.eq(projectId))
                .fetch();
    }
}
