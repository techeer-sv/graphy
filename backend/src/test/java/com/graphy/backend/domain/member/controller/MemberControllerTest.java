package com.graphy.backend.domain.member.controller;

import com.graphy.backend.domain.member.service.MemberService;
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

import java.util.Arrays;
import java.util.List;

import static com.graphy.backend.domain.member.dto.MemberDto.*;
import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@ExtendWith(RestDocumentationExtension.class)
public class MemberControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    MemberService memberService;
    private static String baseUrl = "/api/v1/members";
    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
    }

    @Test
    @DisplayName("올바른 이메일 서식은 회원가입에 성공한다")
    public void joinTest() throws Exception {
        //given
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("yuKeon@gmail.com")
                .password("pwd")
                .nickname("yuKeon")
                .introduction("goodDay")
                .build();

        //when
        doNothing().when(memberService).join(request);

        //then
        mvc.perform(post(baseUrl + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("사용자 등록 성공"))
                .andDo(document("member-join",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("잘못된 이메일 서식은 회원가입에 성공한다")
    public void wrongRequestJoinTest() throws Exception {
        //given
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("yuKeon")
                .password("pwd")
                .nickname("yuKeon")
                .introduction("goodDay")
                .build();

        //when
        doNothing().when(memberService).join(request);

        //then
        mvc.perform(post(baseUrl + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // 400 Bad Request 예상

        verify(memberService, never()).join(request);
    }

//    @Test
//    @DisplayName("로그인에 성공한다")
//    public void testLogin() throws Exception {
//        // given
//        LoginMemberRequest request = LoginMemberRequest.builder()
//                .email("yukeon@gmail.com")
//                .password("pwd")
//                .build();
//
//        TokenInfo tokenInfo = TokenInfo.builder()
//                .grantType("Bearer")
//                .accessToken("accessToken")
//                .refreshToken("refreshToken")
//                .build();
//
//        // when
//        when(memberService.login(new ,  request)).thenReturn(tokenInfo);
//
//        // then
//        mvc.perform(post(baseUrl + "/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andDo(document("member-login",
//                        preprocessResponse(prettyPrint()))
//                );
//    }

    @Test
    @DisplayName("사용자 조회 테스트")
    public void findMemberTest() throws Exception {
        // given
        String nickname = "yuKeon";
        GetMemberResponse member1 = GetMemberResponse.builder()
                .nickname("yuKeon")
                .email("yuKeon@gmail.com")
                .build();
        GetMemberResponse member2 = GetMemberResponse.builder()
                .nickname("youKeon")
                .email("youKeon@gmail.com")
                .build();
        List<GetMemberResponse> result = Arrays.asList(member1, member2);

        // when
        when(memberService.findMember(nickname)).thenReturn(result);

        // then
        mvc.perform(get(baseUrl)
                        .param("nickname", nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("사용자 조회 성공"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].nickname").value("yuKeon"))
                .andExpect(jsonPath("$.data[0].email").value("yuKeon@gmail.com"))
                .andExpect(jsonPath("$.data[1].nickname").value("youKeon"))
                .andExpect(jsonPath("$.data[1].email").value("youKeon@gmail.com"))
                .andDo(document("member-get",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("마이페이지 테스트")
    public void getMyPageTest() throws Exception {
        GetMyPageResponse result = GetMyPageResponse.builder()
                .nickname("yu")
                .introduction("keon")
                .followerCount(10)
                .followingCount(20)
                .projectInfoList(Arrays.asList(
                        ProjectInfo.builder().id(1L).projectName("project1").description("description1").build(),
                        ProjectInfo.builder().id(2L).projectName("project2").description("description2").build()
                ))
                .build();

        // when
        when(memberService.myPage()).thenReturn(result);

        // then
        mvc.perform(get(baseUrl + "/myPage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("마이페이지 조회 성공"))
                .andExpect(jsonPath("$.data.nickname").value("yu"))
                .andExpect(jsonPath("$.data.introduction").value("keon"))
                .andExpect(jsonPath("$.data.followerCount").value(10))
                .andExpect(jsonPath("$.data.followingCount").value(20))
                .andExpect(jsonPath("$.data.projectInfoList", hasSize(2)))
                .andExpect(jsonPath("$.data.projectInfoList[0].id").value(1))
                .andExpect(jsonPath("$.data.projectInfoList[0].projectName").value("project1"))
                .andExpect(jsonPath("$.data.projectInfoList[0].description").value("description1"))
                .andExpect(jsonPath("$.data.projectInfoList[1].id").value(2))
                .andExpect(jsonPath("$.data.projectInfoList[1].projectName").value("project2"))
                .andExpect(jsonPath("$.data.projectInfoList[1].description").value("description2"))
                .andDo(document("member-myPage",
                        preprocessResponse(prettyPrint()))
                );
    }
}
