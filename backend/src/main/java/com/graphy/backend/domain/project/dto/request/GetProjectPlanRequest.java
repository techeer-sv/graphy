package com.graphy.backend.domain.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectPlanRequest {
    @NotBlank(message = "topic cannot be blank")
    private String topic;

    @Size(min=1, max=5, message = "features are not the right number.")
    private List<String> features;

    @Size(min=1, max=15, message = "techStacks are not the right number.")
    private List<String> techStacks;

    @Size(min=1, max=5, message = "plans are not the right number.")
    private List<String> plans;
}