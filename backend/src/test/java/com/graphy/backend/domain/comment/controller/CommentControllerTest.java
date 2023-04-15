package com.graphy.backend.domain.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentRequest;
import static com.graphy.backend.domain.project.dto.ProjectDto.CreateProjectRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;


    @BeforeEach
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .dispatchOptions(true);
        this.mvc = builder.build();
    }

    @Test
    @DisplayName("댓글 수정 API 테스트")
    void 댓글_수정API_테스트() throws Exception {
        // given
        String content = "댓글 내용";

        Long projectId = 1L;
        String projectName = "프로젝트 이름";
        String projContent = "프로젝트 내용";
        String projDescription = "프로젝트 한 줄 소개";

        String updatedContent = "수정된 내용";

        CreateCommentRequest commentRequest = new CreateCommentRequest(content, projectId, null);
        CreateProjectRequest projectRequest = CreateProjectRequest.builder().projectName(projectName).content(projContent)
                .description(projDescription).techTags(null).thumbNail(null).build();

        projectService.createProject(projectRequest);
        commentService.createComment(commentRequest);

        String body = objectMapper.writeValueAsString(new CommentDto.UpdateCommentRequest(updatedContent));


        // then

        ResultActions resultActions = mvc.perform(put("/api/v1/comments/{commentId}", 1L).content(body).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}