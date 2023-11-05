package com.graphy.backend.domain.project.dto.response;

import com.graphy.backend.domain.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectRankingResponse {
    private Long id;
    private String projectName;
    private int viewCount;
    private int likeCount;

    public static GetProjectRankingResponse from(Project project) {
        return GetProjectRankingResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .viewCount(project.getViewCount())
                .likeCount(project.getLikeCount())
                .build();
    }
}