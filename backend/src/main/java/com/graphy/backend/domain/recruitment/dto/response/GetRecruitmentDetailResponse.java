package com.graphy.backend.domain.recruitment.dto.response;

import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.ProcessType;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentDetailResponse {
    private Long id;

    private String title;

    private String content;

    private ProcessType type;

    private LocalDateTime endDate;

    private LocalDateTime period;

    private Position position;

    private int recruitmentCount;

    private int viewCount;

    private boolean isRecruiting;

    private List<String> techTags;


    public static GetRecruitmentDetailResponse from(Recruitment recruitment) {
        return GetRecruitmentDetailResponse.builder()
                .id(recruitment.getId())
                .title(recruitment.getTitle())
                .content(recruitment.getContent())
                .type(recruitment.getType())
                .endDate(recruitment.getEndDate())
                .period(recruitment.getPeriod())
                .position(recruitment.getPosition())
                .recruitmentCount(recruitment.getRecruitmentCount())
                .isRecruiting(recruitment.getIsRecruiting())
                .viewCount(recruitment.getViewCount())
                .techTags(recruitment.getTagNames())
                .build();
    }
}
