package com.graphy.backend.domain.follow.repository;

import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;

import java.util.List;

public interface FollowCustomRepository {
    List<GetMemberListResponse> findFollowers(Long toId);
    List<GetMemberListResponse> findFollowings(Long fromId);
}
