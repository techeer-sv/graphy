package com.graphy.backend.domain.job.repository;

import com.graphy.backend.domain.job.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Override
    Page<Job> findAll(Pageable pageable);
}
