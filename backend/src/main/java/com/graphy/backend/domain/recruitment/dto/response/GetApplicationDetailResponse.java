package com.graphy.backend.domain.recruitment.dto.response;

import com.graphy.backend.domain.recruitment.domain.Application;
import com.graphy.backend.domain.recruitment.dto.request.TechLevelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetApplicationDetailResponse {
    private Long id;
    private String memberNickname;
    private String introduction;
    private String github;
    private List<TechLevelDto> techLevels;

    public static GetApplicationDetailResponse of(Application application, List<TechLevelDto> techLevels) {
        return GetApplicationDetailResponse.builder()
                .id(application.getId())
                .memberNickname(application.getMember().getNickname())
                .introduction(application.getIntroduction())
                .github(application.getGithub())
                .techLevels(techLevels)
                .build();
    }
}
