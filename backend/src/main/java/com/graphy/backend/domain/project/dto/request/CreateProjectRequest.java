package com.graphy.backend.domain.project.dto.request;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.ProjectTags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequest {

    @NotBlank(message = "project name cannot be blank")
    private String projectName;

    @NotBlank(message = "content cannot be blank")
    private String content;

    @NotBlank(message = "description cannot be blank")
    private String description;

    private List<String> techTags;

    private String thumbNail;

    public Project toEntity(Member member) {
        return Project.builder()
                .member(member)
                .projectName(projectName)
                .content(content)
                .description(description)
                .thumbNail(thumbNail)
                .projectTags(new ProjectTags())
                .build();
    }
}