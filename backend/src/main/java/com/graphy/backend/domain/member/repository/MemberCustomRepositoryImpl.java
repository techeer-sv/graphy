package com.graphy.backend.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.graphy.backend.domain.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public void increaseFollowerCount(Long toId) {
        jpaQueryFactory.update(member)
                .set(member.followerCount, member.followerCount.add(1))
                .where(member.id.eq(toId))
                .execute();
    }

    @Override
    public void increaseFollowingCount(Long fromId) {
        jpaQueryFactory.update(member)
                .set(member.followingCount, member.followingCount.add(1))
                .where(member.id.eq(fromId))
                .execute();
    }

    @Override
    public void decreaseFollowerCount(Long toId) {
        jpaQueryFactory.update(member)
                .set(member.followerCount,
                        // 팔로워가 0명이면 0, 아니면 -1
                        member.followerCount
                                .when(0)
                                .then(0)
                                .otherwise(member.followerCount.subtract(1))
                )
                .where(member.id.eq(toId))
                .execute();
    }

    @Override
    public void decreaseFollowingCount(Long fromId) {
        jpaQueryFactory.update(member)
                .set(member.followingCount,
                        member.followingCount
                                .when(0)
                                .then(0)
                                .otherwise(member.followingCount.subtract(1))
                )
                .where(member.id.eq(fromId))
                .execute();
    }
}
