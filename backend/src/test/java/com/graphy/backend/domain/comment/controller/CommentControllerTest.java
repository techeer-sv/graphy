package com.graphy.backend.domain.comment.controller;

import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest extends MockApiTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CommentService commentService;

    @MockBean
    private ProjectService projectService;


    @BeforeEach
    public void setup() {
        this.mvc = buildMockMvc(context);
    }

    @Test
    @DisplayName("댓글 수정 API 테스트")
    void updateCommentTest() throws Exception {
        // given
        Long commentId = 1L;

        String updatedContent = "수정된 내용";

        CommentDto.UpdateCommentRequest commentRequest = new CommentDto.UpdateCommentRequest(updatedContent);

        given(commentService.updateComment(commentId, commentRequest)).willReturn(commentId);

        // when
        String body = objectMapper.writeValueAsString(commentRequest);
        ResultActions resultActions = mvc.perform(put("/api/v1/comments/{commentId}", 1L).content(body).contentType(MediaType.APPLICATION_JSON));


        // then
        resultActions.andExpect((status().isOk()));
    }
}