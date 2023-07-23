package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.auth.jwt.TokenProvider;
import com.graphy.backend.global.auth.redis.repository.RefreshTokenRepository;
import com.graphy.backend.global.common.PageRequest;
import com.graphy.backend.global.config.SecurityConfig;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static com.graphy.backend.domain.project.dto.ProjectDto.GetProjectDetailResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@ExtendWith(RestDocumentationExtension.class)
@WithMockUser(username = "yukeon@gmail.com")
@Import(SecurityConfig.class)
class ProjectControllerTest extends MockApiTest {


    @Autowired
    private WebApplicationContext context;
    @MockBean
    ProjectService projectService;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    CommentService commentService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    private static String baseUrl = "/api/v1/projects";

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .apply(documentationConfiguration(provider))
                .build();
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
            public String getNickname() {
                return null;
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
    @DisplayName("프로젝트 생성 테스트")
    public void createProject() throws Exception {
        //given
        CreateProjectRequest request = CreateProjectRequest.builder()
                .projectName("projectName")
                .description("description")
                .content("content")
                .build();

        CreateProjectResponse response = CreateProjectResponse.builder().projectId(1L).build();

        //when
        when(projectService.createProject(request)).thenReturn(response);

        //then
        mvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("project-create",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("프로젝트 삭제 테스트")

    public void deleteProject() throws Exception {
        //given
        Long projectId = 1L;

        doNothing().when(projectService).deleteProject(anyLong());

        mvc.perform(delete(baseUrl + "/{projectId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로젝트 이름/내용/전체 검색한다")
    void searchProjectsWithName() throws Exception {

        // given
        String projectName = "검색이름";

        GetProjectsRequest request = GetProjectsRequest.builder()
                .projectName(projectName).build();

        PageRequest pageRequest = new PageRequest();
        List<GetProjectResponse> result = new ArrayList<GetProjectResponse>();

        for (int i = 0; i < 5; i++) {
            GetProjectResponse response =
                    GetProjectResponse.builder().id((long) i).projectName("검색이름" + i)
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

    @Test
    @DisplayName("프로젝트 고도화 계획을 제안 받는다")
    void getProjectPlan() throws Exception {

        // given
        List<String> features = new ArrayList<>(Arrays.asList("게시물 업로드", "좋아요 누르는 기능"));
        List<String> techStacks = new ArrayList<>(Arrays.asList("Springboot", "React", "mySQL"));
        List<String> plans = new ArrayList<>(Arrays.asList("Spring Security", "Docker"));
        String topic = "간단한 게시판";

        GetPlanRequest request = new GetPlanRequest(topic, features, techStacks, plans);

        String apiResult = "API 결과";
        CompletableFuture<String> result = CompletableFuture.completedFuture(apiResult);

        given(projectService.getProjectPlanAsync(any())).willReturn(result);

        // when
        String body = objectMapper.writeValueAsString(request);
        ResultActions resultActions = mvc.perform(post(baseUrl+"/plans").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect((status().isOk()));
    }

    @Test
    @DisplayName("프로젝트 조회 시 프로젝트가 존재하지 않으면 예외가 발생한다")
    public void EmptyResultTest() throws Exception {

        // given
        GetProjectsRequest request = GetProjectsRequest.builder().build();
        PageRequest pageRequest = new PageRequest();
        Pageable pageable = pageRequest.of();

        // when
        when(projectService.getProjects(any(GetProjectsRequest.class), any(Pageable.class)))
                .thenReturn(Collections.emptyList());

        // then
        mvc.perform(get(baseUrl + "/search")  // "/project/search" should be replaced with the actual URL
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());  // adjust based on the actual error code you're using
    }
}
