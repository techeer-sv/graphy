package com.graphy.backend.domain.project.dto.request;

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
public class UpdateProjectRequest {

    @NotBlank(message = "project name cannot be blank")
    private String projectName;

    @NotBlank(message = "content cannot be blank")
    private String content;

    @NotBlank(message = "description cannot be blank")
    private String description;

    private List<String> techTags;

    private String thumbNail;
}