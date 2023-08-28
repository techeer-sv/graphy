package com.graphy.backend.domain.job.controller;


import com.graphy.backend.domain.job.service.JobService;
import com.graphy.backend.domain.auth.infra.TokenProvider;
import com.graphy.backend.domain.auth.repository.RefreshTokenRepository;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

@WebMvcTest(JobController.class)
@ExtendWith(RestDocumentationExtension.class)
public class JobControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    JobService jobService;
    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
    }


    @Test
    @DisplayName("공고를 저장한다.")
    public void saveJobInfo() throws Exception {
        doNothing().when(jobService).save();
        mvc.perform(post("/api/v1/jobs"))
                .andExpect(status().isOk())
                .andDo(document("JobInfo-Save",
                        preprocessResponse(prettyPrint()))
                );
        verify(jobService, times(1)).save();
    }

    @Test
    @DisplayName("공고를 삭제한다.")
    public void deleteJobInfo() throws Exception {
        doNothing().when(jobService).deleteExpiredJobs();
        mvc.perform(delete("/api/v1/jobs"))
                .andExpect(status().isOk())
                .andDo(document("JobInfo-Delete",
                        preprocessResponse(prettyPrint()))
                );
        verify(jobService, times(1)).deleteExpiredJobs();
    }
}
