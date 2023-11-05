package com.graphy.backend.domain.job.controller;

import com.graphy.backend.domain.job.dto.response.GetJobResponse;
import com.graphy.backend.domain.job.service.JobService;
import com.graphy.backend.global.common.dto.PageRequest;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "EmploymentController", description = "신규 채용 공고 API")
@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JobController {
    private final JobService jobService;

    @Operation(summary = "findJobList", description = "채용공고 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> jobList(PageRequest pageRequest) {
        Pageable pageable = pageRequest.jobOf();
        List<GetJobResponse> result = jobService.findJobList(pageable);
        if (result.isEmpty()) throw new EmptyResultException(ErrorCode.JOB_DELETED_OR_NOT_EXIST);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.JOB_PAGING_GET_SUCCESS, result));
    }
}
