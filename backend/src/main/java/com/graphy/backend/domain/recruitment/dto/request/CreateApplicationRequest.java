package com.graphy.backend.domain.recruitment.dto.request;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.recruitment.domain.Application;
import com.graphy.backend.domain.recruitment.domain.ApplicationTags;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationRequest {
    @NotBlank(message = "introduction cannot be blank")
    private String introduction;

    @NotBlank(message = "github cannot be blank")
    private String github;

    @NotNull(message = "recruitmentId cannot be null")
    private Long recruitmentId;

    @Valid
    private List<TechLevelDto> techLevels;

    public Application toEntity(Recruitment recruitment, Member member) {
        return Application.builder()
                .introduction(introduction)
                .github(github)
                .recruitment(recruitment)
                .member(member)
                .position(recruitment.getPosition())
                .applicationTags(new ApplicationTags())
                .build();
    }
}
