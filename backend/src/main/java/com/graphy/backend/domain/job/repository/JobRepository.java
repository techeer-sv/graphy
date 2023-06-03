package com.graphy.backend.domain.job.repository;

import com.graphy.backend.domain.job.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Modifying
    @Query("DELETE FROM Job j WHERE j.expirationDate < :today")
    void deleteAllExpiredSince(LocalDateTime today);
}
