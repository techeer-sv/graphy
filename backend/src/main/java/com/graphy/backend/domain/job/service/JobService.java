package com.graphy.backend.domain.job.service;

import com.graphy.backend.domain.job.domain.Job;
import com.graphy.backend.domain.job.dto.response.GetJobResponse;
import com.graphy.backend.domain.job.repository.JobRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public List<GetJobResponse> findJobList(Pageable pageable) {
        Page<Job> jobs = jobRepository.findAll(pageable);
        return GetJobResponse.listOf(jobs).getContent();
    }
}
