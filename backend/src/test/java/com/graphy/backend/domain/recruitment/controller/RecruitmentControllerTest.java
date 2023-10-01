package com.graphy.backend.domain.recruitment.controller;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.ProcessType;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import com.graphy.backend.domain.recruitment.dto.request.CreateRecruitmentRequest;
import com.graphy.backend.domain.recruitment.service.RecruitmentService;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentRequest;
import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecruitmentController.class)
@ExtendWith(RestDocumentationExtension.class)
@Import(SecurityConfig.class)
@WithMockCustomUser
public class RecruitmentControllerTest extends MockApiTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    RecruitmentService recruitmentService;
    private Member member;
    private Recruitment recruitment;
    private static final String BASE_URL = "/api/v1/recruitments";

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        member = Member.builder()
                .id(1L)
                .email("graphy@gmail.com")
                .nickname("name")
                .role(Role.ROLE_USER)
                .build();

        recruitment = Recruitment.builder()
                .member(member)
                .title("title")
                .content("content")
                .type(ProcessType.ONLINE)
                .endDate(LocalDateTime.now())
                .period(LocalDateTime.now())
                .position(Position.BACKEND)
                .recruitmentCount(3)
                .build();

        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
    }
    @Test
    @DisplayName("구인 게시글 생성 테스트")
    void recruitmentAddTest() throws Exception {
        // given
        CreateRecruitmentRequest request = CreateRecruitmentRequest.builder()
                .title("title")
                .content("content")
                .type(ProcessType.ONLINE)
                .endDate(LocalDateTime.now())
                .period(LocalDateTime.now())
                .position(Position.BACKEND)
                .recruitmentCount(3)
                .techTags(List.of("Spring", "Vue", "Docker"))
                .build();


        // when, then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("recruitment/add/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("title").description("구인 게시글 제목"),
                                fieldWithPath("content").description("구인 게시글 내용"),
                                fieldWithPath("type").description("프로젝트 진행 방식"),
                                fieldWithPath("endDate").description("구인 게시글 마감 일정"),
                                fieldWithPath("period").description("프로젝트 진행 기간"),
                                fieldWithPath("position").description("모집 분야"),
                                fieldWithPath("recruitmentCount").description("모집 인원"),
                                fieldWithPath("techTags").description("기술 스택")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }
}
