package com.graphy.backend.domain.job.controller;

import com.graphy.backend.domain.job.dto.response.GetJobResponse;
import com.graphy.backend.domain.job.service.JobService;
import com.graphy.backend.global.common.PageRequest;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "findJobList", description = "채용공고 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> jobList(PageRequest pageRequest) {
        Pageable pageable = pageRequest.jobOf();
        List<GetJobResponse> result = jobService.findJobList(pageable);
        if (result.isEmpty()) throw new EmptyResultException(ErrorCode.JOB_DELETED_OR_NOT_EXIST);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.JOB_PAGING_GET_SUCCESS, result));
    }
}
