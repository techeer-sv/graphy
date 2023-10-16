package com.graphy.backend.domain.job.dto.response;

import com.graphy.backend.domain.job.domain.Job;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetJobResponse {

    private Long id;

    private String companyName;

    private String title;

    private String url;

    private LocalDateTime expirationDate;

    public static GetJobResponse from(Job job) {
        return GetJobResponse.builder()
            .id(job.getId())
            .companyName(job.getCompanyName())
            .title(job.getTitle())
            .url(job.getUrl())
            .expirationDate(job.getExpirationDate())
            .build();
    }

    public static Page<GetJobResponse> listOf(Page<Job> jobs) {
        return jobs.map(GetJobResponse::from);
    }
}
