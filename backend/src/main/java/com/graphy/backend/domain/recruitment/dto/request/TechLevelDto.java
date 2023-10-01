package com.graphy.backend.domain.recruitment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechLevelDto {
    @NotBlank(message = "tech cannot be blank")
    private String tech;

    @Min(value = 1, message = "최소 숙련도는 1입니다.")
    @Max(value = 5, message = "최대 숙련도는 5입니다.")
    @NotNull(message = "level cannot be null")
    private Integer level;
}
