package com.graphy.backend.domain.job.service;

import com.graphy.backend.domain.job.domain.Job;
import com.graphy.backend.domain.job.repository.JobRepository;
import com.graphy.backend.test.MockApiTest;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest extends MockTest {
    @InjectMocks
    JobService jobService;

    @Mock
    JobRepository jobRepository;


    @Test
    @DisplayName("공고가 삭제된다.")
    public void saveTest() throws Exception {
        doNothing().when(jobRepository).deleteAllExpiredSince(any(LocalDateTime.class));

        jobService.deleteExpiredJobs();

        verify(jobRepository, times(1)).deleteAllExpiredSince(any(LocalDateTime.class));
    }
}
