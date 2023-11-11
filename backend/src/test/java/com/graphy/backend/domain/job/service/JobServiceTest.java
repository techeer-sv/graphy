package com.graphy.backend.domain.job.service;

import com.graphy.backend.domain.job.repository.JobRepository;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest extends MockTest {
    @InjectMocks
    JobService jobService;

    @Mock
    JobRepository jobRepository;

}
