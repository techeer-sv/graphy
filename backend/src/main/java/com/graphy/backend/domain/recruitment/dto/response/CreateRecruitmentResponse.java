package com.graphy.backend.domain.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecruitmentResponse {

    private Long recruitmentId;

    public static CreateRecruitmentResponse from(Long recruitmentId) {
        return CreateRecruitmentResponse.builder()
                .recruitmentId(recruitmentId).build();
    }
}
