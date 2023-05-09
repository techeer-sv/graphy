package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.chatgpt.service.GPTChatRestService;
import com.graphy.backend.global.common.PageRequest;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.graphy.backend.domain.project.dto.ProjectDto.GetProjectDetailResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@ExtendWith(RestDocumentationExtension.class)
class ProjectControllerTest extends MockApiTest {


    @Autowired
    private WebApplicationContext context;
    @MockBean
    ProjectService projectService;

    @MockBean
    GPTChatRestService gptChatRestService;

    @MockBean
    CommentService commentService;
    private static String baseUrl = "/api/v1/projects";

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
    }


    @Test
    @DisplayName("프로젝트 조회 시 댓글도 조회된다")
    void getProjectWithComments() throws Exception {

        //given
        CommentWithMaskingDto dto = new CommentWithMaskingDto() {
            @Override
            public String getContent() {
                return "testComment";
            }

            @Override
            public Long getCommentId() {
                return null;
            }

            @Override
            public Integer getChildCount() {
                return null;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }
        };
        List<CommentWithMaskingDto> list = new ArrayList<>();
        list.add(dto);

        given(projectService.getProjectById(1L))
                .willReturn(GetProjectDetailResponse.builder()
                        .projectName("project")
                        .commentsList(list).build());



        //then
        ResultActions result = mvc.perform(get(baseUrl + "/{projectId}", 1L));
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로젝트 이름/내용/전체 검색한다")
    void searchProjectsWithName() throws Exception {

        // given
        String projectName = "검색이름";

        ProjectDto.GetProjectsRequest request = ProjectDto.GetProjectsRequest.builder()
                .projectName(projectName).build();

        com.graphy.backend.global.common.PageRequest pageRequest = new PageRequest();
        List<ProjectDto.GetProjectResponse> result = new ArrayList<ProjectDto.GetProjectResponse>();

        for (int i = 0; i < 5; i++) {
            ProjectDto.GetProjectResponse response =
                    ProjectDto.GetProjectResponse.builder().id((long) i).projectName("검색이름" + i)
                            .description("프로젝트 설명" + i).createdAt(LocalDateTime.now()).build();
            result.add(response);
        }

        given(projectService.getProjects(any(), any(Pageable.class))).willReturn(result);

        // when
        String body = objectMapper.writeValueAsString(request);
        ResultActions resultActions = mvc.perform(get(baseUrl+"/search")
                .param("projectName", projectName).param("pageRequest", pageRequest.toString()).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect((status().isOk()));
    }
}
