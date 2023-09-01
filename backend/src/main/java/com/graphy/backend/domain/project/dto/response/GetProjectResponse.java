package com.graphy.backend.domain.project.dto.response;

import com.graphy.backend.domain.member.dto.response.GetMemberResponse;
import com.graphy.backend.domain.project.domain.Project;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectResponse {

    private Long id;

    private GetMemberResponse member;

    private String projectName;

    private String description;

    private String thumbNail;

    private LocalDateTime createdAt;

    private List<String> techTags;

    public static GetProjectResponse from(Project project) {
        return GetProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .techTags(project.getTagNames())
                .member(GetMemberResponse.from(project.getMember()))
                .thumbNail(project.getThumbNail())
                .build();
    }

    public static Page<GetProjectResponse> listOf(Page<Project> projects) {
        return projects.map(GetProjectResponse::from);
    }
}