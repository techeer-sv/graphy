package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;

import java.util.List;

public interface LikeCustomRepository {

    List<GetMemberListResponse> findLikedMembers(Long projectId);
}
