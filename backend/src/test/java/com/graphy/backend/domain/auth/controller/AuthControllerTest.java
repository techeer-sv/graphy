package com.graphy.backend.domain.auth.controller;

import com.graphy.backend.domain.auth.dto.request.LogoutRequest;
import com.graphy.backend.domain.auth.dto.response.GetTokenInfoResponse;
import com.graphy.backend.domain.auth.service.AuthService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.dto.request.SignInMemberRequest;
import com.graphy.backend.domain.member.dto.request.SignUpMemberRequest;
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
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentRequest;
import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ExtendWith(RestDocumentationExtension.class)
public class AuthControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AuthService authService;

    private Member member;
    private String BASE_URL = "/api/v1/auth";

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        member = Member.builder()
                .id(1L)
                .email("graphy@gmail.com")
                .password("password")
                .nickname("name")
                .role(Role.ROLE_USER)
                .build();

        this.mvc = buildMockMvc(context, provider);
    }

    @Test
    @DisplayName("회원가입을 한다")
    public void signUpTest() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.builder()
                .email("google@naver.kakao")
                .nickname("nickname")
                .password("password")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())

                .andDo(print())
                .andDo(document("auth/signUp/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("introduction").description("본인 소개").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @DisplayName("회원가입 시 이메일이 공백이면 예외가 발생한다")
    public void signUpWithEmptyEmailExceptionTest() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.builder()
                .email("")
                .nickname("nickname")
                .password("password")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signUp/fail/emptyEmail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("introduction").description("본인 소개").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("회원가입 시 이메일 형식이 올바르지 않은 경우 예외가 발생한다")
    public void signUpWithInvalidEmailFormatExceptionTest() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.builder()
                .email("InvalidEmailFormat")
                .nickname("nickname")
                .password("password")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signUp/fail/invalidEmail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("introduction").description("본인 소개").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("회원가입 시 닉네임이 공백이면 예외가 발생한다")
    public void signUpWithEmptyNicknameExceptionTest() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.builder()
                .email("google@naver.kakao")
                .nickname("")
                .password("password")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signUp/fail/emptyNickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("introduction").description("본인 소개").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("회원가입 시 비밀번호가 공백이면 예외가 발생한다")
    public void signUpWithEmptyPasswordExceptionTest() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.builder()
                .email("google@naver.kakao")
                .nickname("nickname")
                .password("")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signUp/fail/emptyPassword",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("introduction").description("본인 소개").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("로그인을 한다")
    public void signInTest() throws Exception {
        // given
        SignInMemberRequest request = SignInMemberRequest.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();

        GetTokenInfoResponse response = GetTokenInfoResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .grantType("Bearer")
                .build();

        // when
        when(authService.signIn(any(SignInMemberRequest.class))).thenReturn(response);

        // then
        mvc.perform(post(BASE_URL + "/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("A002"))
                .andExpect(jsonPath("$.message").value("로그인 성공"))
                .andExpect(jsonPath("$.data.accessToken").value(response.getAccessToken()))
                .andExpect(jsonPath("$.data.refreshToken").value(response.getRefreshToken()))
                .andExpect(jsonPath("$.data.grantType").value(response.getGrantType()))

                .andDo(print())
                .andDo(document("auth/signIn/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),

                                fieldWithPath("data.refreshToken").description("리프래시 토큰"),
                                fieldWithPath("data.accessToken").description("액세스 토큰"),
                                fieldWithPath("data.grantType").description("인증 유형")
                        )));
    }

    @Test
    @DisplayName("로그인 시 이메일이 공백이면 예외가 발생한다")
    public void signInWithEmptyEmailExceptionTest() throws Exception {
        // given
        SignInMemberRequest request = SignInMemberRequest.builder()
                .email("")
                .password("password")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signIn/fail/emptyEmail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("로그인 시 이메일 형식이 올바르지 않은 경우 예외가 발생한다")
    public void signInWithInvalidEmailFormatExceptionTest() throws Exception {
        // given
        SignInMemberRequest request = SignInMemberRequest.builder()
                .email("InvalidEmailFormat")
                .password("password")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signIn/fail/invalidEmail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("로그인 시 비밀번호가 공백이면 예외가 발생한다")
    public void signInWithEmptyPasswordExceptionTest() throws Exception {
        // given
        SignInMemberRequest request = SignInMemberRequest.builder()
                .email("google@naver.kakao")
                .password("")
                .build();

        // when, then
        mvc.perform(post(BASE_URL + "/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/signIn/fail/emptyPassword",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("로그아웃을 한다")
    public void logoutTest() throws Exception {
        // given
        LogoutRequest request = LogoutRequest.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();


        // when, then
        mvc.perform(post(BASE_URL + "/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())

                .andDo(print())
                .andDo(document("auth/logout/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("accessToken").description("액세스 토큰"),
                                fieldWithPath("refreshToken").description("리프래시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @DisplayName("로그아웃 시 액세스 토큰이 공백이면 예외가 발생한다")
    public void logoutEmptyAccessTokenExceptionTest() throws Exception {
        // given
        LogoutRequest request = LogoutRequest.builder()
                .accessToken("")
                .refreshToken("refreshToken")
                .build();


        // when, then
        mvc.perform(post(BASE_URL + "/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/logout/fail/emptyAccessToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("accessToken").description("액세스 토큰"),
                                fieldWithPath("refreshToken").description("리프래시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("로그아웃 시 리프래시 토큰이 공백이면 예외가 발생한다")
    public void logoutEmptyRefreshTokenExceptionTest() throws Exception {
        // given
        LogoutRequest request = LogoutRequest.builder()
                .accessToken("accessToken")
                .refreshToken("")
                .build();


        // when, then
        mvc.perform(post(BASE_URL + "/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("auth/logout/fail/emptyRefreshToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("accessToken").description("액세스 토큰"),
                                fieldWithPath("refreshToken").description("리프래시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("errors").description("에러 내용"),

                                fieldWithPath("errors[].field").description("문제가 발생한 필드").optional(),
                                fieldWithPath("errors[].value").description("해당 필드의 입력 값").optional(),
                                fieldWithPath("errors[].reason").description("해당 필드에 대한 에러 설명").optional()
                        )));
    }

    @Test
    @DisplayName("토큰을 재발급 받는다")
    public void reIssueTest() throws Exception {
        // given
        GetTokenInfoResponse response = GetTokenInfoResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        // when
        when(authService.reissue(any(HttpServletRequest.class), any())).thenReturn(response);

        // then
        mvc.perform(post(BASE_URL + "/reissue")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andDo(print())
                .andDo(document("auth/reissue/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),

                                fieldWithPath("data.refreshToken").description("리프래시 토큰"),
                                fieldWithPath("data.accessToken").description("액세스 토큰"),
                                fieldWithPath("data.grantType").description("인증 유형")
                        )));
    }
}
