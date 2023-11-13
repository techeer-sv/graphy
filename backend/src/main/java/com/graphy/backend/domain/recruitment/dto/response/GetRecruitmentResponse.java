package com.graphy.backend.domain.recruitment.dto.response;

import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentResponse {

    private Long id;

    private String nickname;

    private String title;

    private Position position;

    private boolean isRecruiting;

    private List<String> techTags;

    public static GetRecruitmentResponse from(Recruitment recruitment) {
        return GetRecruitmentResponse.builder()
                .id(recruitment.getId())
                .nickname(recruitment.getMember().getNickname())
                .title(recruitment.getTitle())
                .position(recruitment.getPosition())
                .isRecruiting(recruitment.getIsRecruiting())
                .techTags(recruitment.getTagNames())
                .build();
    }

    public static List<GetRecruitmentResponse> listOf(List<Recruitment> recruitments) {
        return recruitments.stream()
                .map(GetRecruitmentResponse::from)
                .collect(Collectors.toList());
    }
}
