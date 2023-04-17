package com.graphy.backend.domain.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;
import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ProjectService projectService;

    @MockBean
    CommentService commentService;

    private static String baseUrl = "/api/v1/projects";



    @Test
    public void 댓글과_대댓글이_조회된다() throws Exception {
        //given
        GetCommentsResponse getCommentsResponse1 = new GetCommentsResponse(1L, 3L, "content", null);
        GetCommentsResponse getCommentsResponse2 = new GetCommentsResponse(1L, 4L, "content2", null);

        List<GetCommentsResponse> list = new ArrayList<>();
        list.add(getCommentsResponse1);
        list.add(getCommentsResponse2);

        GetProjectDetailResponse build = GetProjectDetailResponse.builder().projectName("name")
                .id(2L).commentsList(list).build();

        given(projectService.getProjectById(1L)).willReturn(build);


        mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl + "/{projectId}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
