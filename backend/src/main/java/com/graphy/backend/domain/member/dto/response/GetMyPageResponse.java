package com.graphy.backend.domain.member.dto.response;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.dto.response.GetProjectInfoResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetMyPageResponse {
    private Long id;
    private String nickname;
    private String introduction;
    private int followerCount;
    private int followingCount;
    private List<GetProjectInfoResponse> getProjectInfoResponseList;

    public static GetMyPageResponse of(Member member,
                                       List<GetProjectInfoResponse> getProjectInfoResponseList) {
        return GetMyPageResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .followerCount(member.getFollowerCount())
                .followingCount(member.getFollowingCount())
                .getProjectInfoResponseList(getProjectInfoResponseList)
                .build();
    }
}