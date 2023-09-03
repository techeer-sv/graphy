package com.graphy.backend.domain.follow.repository.custom;

import com.graphy.backend.domain.follow.repository.FollowCustomRepository;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.graphy.backend.domain.follow.domain.QFollow.follow;
import static com.graphy.backend.domain.member.domain.QMember.member;

@RequiredArgsConstructor
public class FollowCustomRepositoryImpl implements FollowCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GetMemberListResponse> findFollowers(Long toId) {
        return jpaQueryFactory.select(Projections.constructor(GetMemberListResponse.class,
                member.id,
                member.nickname))
                .from(member)
                .innerJoin(follow)
                .on(follow.fromId.eq(member.id))
                .where(follow.toId.eq(toId))
                .fetch();
    }

    @Override
    public List<GetMemberListResponse> findFollowings(Long fromId) {
        return jpaQueryFactory.select(Projections.constructor(GetMemberListResponse.class,
                        member.id,
                        member.nickname))
                .from(member)
                .innerJoin(follow)
                .on(follow.toId.eq(member.id))
                .where(follow.fromId.eq(fromId))
                .fetch();
    }
}
