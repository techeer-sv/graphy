package com.graphy.backend.domain.job.dto;

import com.graphy.backend.domain.job.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class JobDto {

    @Getter
    @AllArgsConstructor
    public static class CreateJobInfoRequest {
        private Long id;
        private String companyName;

        private String title;

        private String url;

        private LocalDateTime expirationDate;

        public static Job toJob(Long id,
                                String companyName,
                                String title,
                                String url,
                                LocalDateTime expirationDate) {
            return new Job(id, companyName, title, url, expirationDate);
        }
    }
}
