package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.dto.request.CreateProjectRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectPlanRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectsRequest;
import com.graphy.backend.domain.project.dto.response.CreateProjectResponse;
import com.graphy.backend.domain.project.dto.response.GetProjectResponse;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.config.SecurityConfig;
import com.graphy.backend.test.MockApiTest;
import com.graphy.backend.test.util.WithMockCustomUser;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentRequest;
import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@ExtendWith(RestDocumentationExtension.class)
@Import(SecurityConfig.class)
class ProjectControllerTest extends MockApiTest {


    @Autowired
    private WebApplicationContext context;
    @MockBean
    ProjectService projectService;

    @MockBean
    CommentService commentService;

    private static final String BASE_URL = "/api/v1/projects";

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
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

        CreateProjectResponse response = CreateProjectResponse.builder().projectId(1L).build();

        //when
        when(projectService.addProject(request, new Member())).thenReturn(response);

        //then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("project/add/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("projectName").description("프로젝트 이름"),
                                fieldWithPath("description").description("프로젝트 설명"),
                                fieldWithPath("content").description("프로젝트 내용"),
                                fieldWithPath("techTags").description("기술 스택"),
                                fieldWithPath("thumbNail").description("썸네일")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("프로젝트 삭제 테스트")
    void deleteProject() throws Exception {

        mvc.perform(delete(BASE_URL + "/{projectId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("프로젝트 이름/내용/전체 검색한다")
    void searchProjectsWithName() throws Exception {

        // given
        String projectName = "검색이름";


        List<GetProjectResponse> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            GetProjectResponse response =
                    GetProjectResponse.builder().id((long) i).projectName("검색이름" + i)
                            .description("프로젝트 설명" + i).createdAt(LocalDateTime.now()).build();
            result.add(response);
        }

        given(projectService.findProjectList(any(GetProjectsRequest.class), any(Pageable.class))).willReturn(result);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("projectName", projectName);
        info.add("page", "0");
        info.add("size", "5");

        // when & then
        mvc.perform(get(BASE_URL).params(info))
                .andExpect(status().isOk())
                .andDo(document("project/list/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("projectName").description("검색할 프로젝트 이름"),
                                parameterWithName("page").description("page 번호"),
                                parameterWithName("size").description("한 페이지에 보여줄 프로젝트 개수")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data[].id").description("프로젝트 ID"),
                                fieldWithPath("data[].member").description("프로젝트 작성자"),
                                fieldWithPath("data[].projectName").description("답글 ID"),
                                fieldWithPath("data[].description").description("한 줄 소개"),
                                fieldWithPath("data[].thumbNail").description("썸네일"),
                                fieldWithPath("data[].createdAt").description("생성일자"),
                                fieldWithPath("data[].techTags").description("기술 스택")
                        )));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("프로젝트 고도화 계획을 제안 받는다")
    void getProjectPlan() throws Exception {

        // given
        List<String> features = new ArrayList<>(Arrays.asList("게시물 업로드", "좋아요 누르는 기능"));
        List<String> techStacks = new ArrayList<>(Arrays.asList("Springboot", "React", "mySQL"));
        List<String> plans = new ArrayList<>(Arrays.asList("Spring Security", "Docker"));
        String topic = "간단한 게시판";

        GetProjectPlanRequest request = new GetProjectPlanRequest(topic, features, techStacks, plans);

        String apiResult = "API 결과";
        CompletableFuture<String> result = CompletableFuture.completedFuture(apiResult);

        given(projectService.getProjectPlanAsync(any())).willReturn(result);

        // when

        mvc.perform(post(BASE_URL +"/plans")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
