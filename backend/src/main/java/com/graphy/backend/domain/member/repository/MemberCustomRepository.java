package com.graphy.backend.domain.member.repository;

public interface MemberCustomRepository {
    void increaseFollowerCount(Long toId);
    void increaseFollowingCount(Long fromId);
    void decreaseFollowerCount(Long toId);
    void decreaseFollowingCount(Long fromId);
}
