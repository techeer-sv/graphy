package com.graphy.backend.domain.job.dto;

import com.graphy.backend.domain.job.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class JobDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateJobInfoRequest {
        private Long id;
        private String companyName;

        private String title;

        private String url;

        private LocalDateTime expirationDate;

        public Job toEntity() {
            return Job.builder()
                    .id(id)
                    .companyName(companyName)
                    .title(title)
                    .url(url)
                    .expirationDate(expirationDate)
                    .build();
        }
    }
}
