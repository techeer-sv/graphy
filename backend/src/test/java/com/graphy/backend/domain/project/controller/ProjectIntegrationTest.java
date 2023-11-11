package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.project.dto.request.CreateProjectRequest;
import com.graphy.backend.test.util.IntegrationTest;
import com.graphy.backend.test.util.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectIntegrationTest extends IntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private static final String BASE_URL = "/api/v1/projects";

    @BeforeEach
    public void setup() {
        this.mvc = this.buildMockMvc(context);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("프로젝트 생성 테스트")
    void createProject() throws Exception {
        //given
        CreateProjectRequest request = CreateProjectRequest.builder()
                .projectName("projectName")
                .description("description")
                .content("content")
                .build();

        //then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

}
