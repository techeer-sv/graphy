package com.graphy.backend.domain.project.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectsRequest {

    private String projectName;

    private String content;

}