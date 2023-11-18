package com.graphy.backend.domain.recruitment.dto.request;

import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecruitmentRequest {
    @NotBlank(message = "Recruitment title cannot be blank")
    private String title;

    @NotBlank(message = "content cannot be blank")
    private String content;

    @NotNull(message = "ProcessType cannot be null")
    private ProcessType type;

    @NotNull(message = "endDate cannot be null")
    private LocalDateTime endDate;

    @NotNull(message = "period cannot be null")
    private LocalDateTime period;

    @NotNull(message = "isRecruiting cannot be null")
    private Boolean isRecruiting;

    @NotNull(message = "position cannot be null")
    private Position position;

    @Positive(message = "최소 인원은 1명입니다.")
    @NotNull(message = "recruitmentCount cannot be null")
    private Integer recruitmentCount;

    private List<String> techTags;
}
