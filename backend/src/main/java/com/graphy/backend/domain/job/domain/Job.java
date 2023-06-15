package com.graphy.backend.domain.job.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Job {
    @Id
    @Column(name = "job_id")
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Builder
    public Job(Long id,
               String companyName,
               String title,
               String url,
               LocalDateTime expirationDate) {
        this.id = id;
        this.companyName = companyName;
        this.title = title;
        this.url = url;
        this.expirationDate = expirationDate;

    }
}
