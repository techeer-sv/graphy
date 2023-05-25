package com.graphy.backend.domain.job.controller;

import com.graphy.backend.domain.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@Tag(name = "EmploymentController", description = "신규 채용 공고 API")
@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JobController {
    private final JobService jobService;

    @Operation(summary = "Save JobInfo", description = "신규 공고 저장")
    @Scheduled(cron = "0 0 0 */3 * *")
    @PostMapping
    public void save(){
        jobService.save();
    }


    @Operation(summary = "Delete Job", description = "만료일이 지난 공고 삭제")
    @Scheduled(cron = "0 0 0 * * *")
    @DeleteMapping
    public void deleteExpiredJobs(){
        jobService.deleteExpiredJobs();
    }
}
