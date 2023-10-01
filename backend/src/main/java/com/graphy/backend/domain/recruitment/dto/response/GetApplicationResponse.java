package com.graphy.backend.domain.recruitment.dto.response;

import com.graphy.backend.domain.recruitment.domain.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetApplicationResponse {
    private Long id;
    private String memberNickname;
    private String introduction;

    public static GetApplicationResponse from(Application application) {
        return GetApplicationResponse.builder()
                .id(application.getId())
                .memberNickname(application.getMember().getNickname())
                .introduction(application.getIntroduction())
                .build();
    }
}
