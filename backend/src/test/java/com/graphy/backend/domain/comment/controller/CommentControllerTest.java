package com.graphy.backend.domain.comment.controller;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.request.CreateCommentRequest;
import com.graphy.backend.domain.comment.dto.request.UpdateCommentRequest;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;

import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentRequest;
import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentResponse;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@ExtendWith(RestDocumentationExtension.class)
class CommentControllerTest extends MockApiTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private CommentService commentService;


    private Member member;
    private Project project;
    private Comment parentComment;
    private String BASE_URL = "/api/v1/comments";

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        member = Member.builder()
                .id(1L)
                .email("graphy@gmail.com")
                .nickname("name")
                .role(Role.ROLE_USER)
                .build();

        project = Project.builder()
                .id(1L)
                .member(member)
                .build();

        parentComment = Comment.builder()
                .id(1L)
                .content("parentComment")
                .member(member)
                .project(project)
                .build();

        this.mvc = buildMockMvc(context, provider);
    }

    @Test
    @DisplayName("댓글을 생성한다")
    void addCommentTest() throws Exception {
        // given
        CreateCommentRequest request = new CreateCommentRequest("content", project.getId(), null);

        // when, then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("comment/add/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("projectId").description("프로젝트 ID"),
                                fieldWithPath("parentId").description("댓글 ID").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @DisplayName("댓글 생성 시 내용이 없으면 예외가 발생한다")
    void addCommentEmptyContentExceptionTest() throws Exception {
        // given
        CreateCommentRequest request = new CreateCommentRequest("", project.getId(), null);


        // when, then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .principal(new TestingAuthenticationToken(member.getEmail(), member.getPassword())))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("comment/add/fail/emptyContent",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("projectId").description("프로젝트 ID"),
                                fieldWithPath("parentId").description("댓글 ID").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 설명"),
                                fieldWithPath("errors[].field").description("에러가 발생시킨 필드"),
                                fieldWithPath("errors[].value").description("에러를 발생시킨 필드 값"),
                                fieldWithPath("errors[].reason").description("에러가 발생한 원인")
                        )));
    }

    @Test
    @DisplayName("댓글 생성 시 프로젝트 아이디가 없으면 예외가 발생한다")
    void addCommentEmptyProjectIdExceptionTest() throws Exception {
        // given
        CreateCommentRequest request = new CreateCommentRequest("content", null, null);


        // when, then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .principal(new TestingAuthenticationToken(member.getEmail(), member.getPassword())))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("comment/add/fail/emptyProjectId",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("projectId").description("프로젝트 ID"),
                                fieldWithPath("parentId").description("댓글 ID").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 설명"),
                                fieldWithPath("errors[].field").description("에러가 발생시킨 필드"),
                                fieldWithPath("errors[].value").description("에러를 발생시킨 필드 값"),
                                fieldWithPath("errors[].reason").description("에러가 발생한 원인")
                        )));
    }

    @Test
    @DisplayName("답글을 생성한다")
    void addReCommentTest() throws Exception {
        // given
        CreateCommentRequest request = new CreateCommentRequest("content", project.getId(), parentComment.getId());

        // when, then
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("comment/reComment/add/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("projectId").description("프로젝트 ID"),
                                fieldWithPath("parentId").description("댓글 ID").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @DisplayName("답글을 조회한다")
    void findReCommentListTest() throws Exception {
        // given
        GetReplyListResponse response1 = GetReplyListResponse.builder().commentId(1L).content("content1").build();
        GetReplyListResponse response2 = GetReplyListResponse.builder().commentId(2L).content("content2").build();
        GetReplyListResponse response3 = GetReplyListResponse.builder().commentId(3L).content("content3").build();

        List<GetReplyListResponse> responseList = new ArrayList<>(List.of(response1, response2, response3));

        // when
        when(commentService.findCommentList(parentComment.getId())).thenReturn(responseList);

        // then
        mvc.perform(get(BASE_URL + "/{commentId}", parentComment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("C001"))
                .andExpect(jsonPath("$.message").value("답글 조회 성공"))
                .andExpect(jsonPath("$.data[0].content").value(response1.getContent()))
                .andExpect(jsonPath("$.data[0].commentId").value(response1.getCommentId()))
                .andExpect(jsonPath("$.data[1].content").value(response2.getContent()))
                .andExpect(jsonPath("$.data[1].commentId").value(response2.getCommentId()))
                .andExpect(jsonPath("$.data[2].content").value(response3.getContent()))
                .andExpect(jsonPath("$.data[2].commentId").value(response3.getCommentId()))
                .andDo(print())
                .andDo(document("comment/reComment/findAll/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data[].nickname").description("답글 작성자의 닉네임"),
                                fieldWithPath("data[].content").description("답글 내용"),
                                fieldWithPath("data[].commentId").description("답글 ID"),
                                fieldWithPath("data[].createdAt").description("답글 생성 시간")
                        )));
    }

    @Test
    @DisplayName("댓글을 수정한다")
    void modifyCommentTest() throws Exception {
        // given
        UpdateCommentRequest request = new UpdateCommentRequest("updateContent");

        // when, then
        mvc.perform(put(BASE_URL + "/{commentId}", parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("comment/modify/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @DisplayName("댓글을 수정 시 내용이 공백이면 예외가 발생한다")
    void modifyCommentEmptyContentTest() throws Exception {
        // given
        UpdateCommentRequest request = new UpdateCommentRequest("");

        // when, then
        mvc.perform(put(BASE_URL + "/{commentId}", parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("comment/modify/fail/emptyContent",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 설명"),
                                fieldWithPath("errors[].field").description("에러가 발생시킨 필드"),
                                fieldWithPath("errors[].value").description("에러를 발생시킨 필드 값"),
                                fieldWithPath("errors[].reason").description("에러가 발생한 원인")
                        )));
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    void removeCommentTest() throws Exception {

        // given, when, then
        mvc.perform(delete(BASE_URL + "/{commentId}", parentComment.getId()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("comment/remove/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }
}