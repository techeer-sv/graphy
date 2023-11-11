package com.graphy.backend.domain.job.controller;


import com.graphy.backend.domain.job.service.JobService;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(JobController.class)
@ExtendWith(RestDocumentationExtension.class)
class JobControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    JobService jobService;

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
    }
}
