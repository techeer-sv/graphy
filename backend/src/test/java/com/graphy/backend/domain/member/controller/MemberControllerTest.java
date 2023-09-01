package com.graphy.backend.domain.member.controller;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.dto.response.GetMemberResponse;
import com.graphy.backend.domain.member.dto.response.GetMyPageResponse;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.project.dto.response.GetProjectInfoResponse;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@ExtendWith(RestDocumentationExtension.class)
public class MemberControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    MemberService memberService;
    private static String BASE_URL = "/api/v1/members";

    private Member member1;
    private Member member2;
    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);

        member1 = Member.builder()
                .id(1L)
                .email("email1@gmail.com")
                .nickname("name1")
                .introduction("introduction1")
                .followingCount(10)
                .followerCount(11)
                .role(Role.ROLE_USER)
                .build();

        member2 = Member.builder()
                .id(2L)
                .email("email2@gmail.com")
                .nickname("name2")
                .introduction("introduction2")
                .followingCount(10)
                .followerCount(11)
                .role(Role.ROLE_USER)
                .build();
    }


    @Test
    @DisplayName("닉네임으로 사용자를 조회한다")
    public void findMemberTest() throws Exception {
        // given
        GetMemberResponse response1 = GetMemberResponse.builder()
                .nickname(member1.getNickname())
                .email(member1.getEmail())
                .build();

        GetMemberResponse response2 = GetMemberResponse.builder()
                .nickname(member2.getNickname())
                .email(member2.getEmail())
                .build();

        List<GetMemberResponse> result = Arrays.asList(response1, response2);

        // when
        when(memberService.findMemberList(member1.getNickname())).thenReturn(result);

        // then
        mvc.perform(get(BASE_URL)
                        .param("nickname", member1.getNickname())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.code").value("M001"))
                .andExpect(jsonPath("$.message").value("사용자 조회 성공"))

                .andExpect(jsonPath("$.data[0].nickname").value(member1.getNickname()))
                .andExpect(jsonPath("$.data[0].email").value(member1.getEmail()))

                .andExpect(jsonPath("$.data[1].nickname").value(member2.getNickname()))
                .andExpect(jsonPath("$.data[1].email").value(member2.getEmail()))

                .andDo(print())
                .andDo(document("members/find/success",
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),

                                fieldWithPath("data[].nickname").description("닉네임"),
                                fieldWithPath("data[].email").description("이메일 주소")
                        )));
    }

    @Test
    @DisplayName("현재 로그인한 사용자의 정보로 마이페이지를 조회한다")
    public void getMyPageTest() throws Exception {
        GetMyPageResponse result = GetMyPageResponse.builder()
                .nickname(member1.getNickname())
                .introduction(member1.getIntroduction())
                .followerCount(member1.getFollowerCount())
                .followingCount(member1.getFollowingCount())
                .getProjectInfoResponseList(Arrays.asList(
                        GetProjectInfoResponse.builder().id(1L).projectName("project1").description("description1").build(),
                        GetProjectInfoResponse.builder().id(2L).projectName("project2").description("description2").build()
                ))
                .build();

        // when
        when(memberService.myPage(any())).thenReturn(result);


        // then
        mvc.perform(get(BASE_URL + "/mypage")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.data.nickname").value(member1.getNickname()))
                .andExpect(jsonPath("$.data.introduction").value(member1.getIntroduction()))
                .andExpect(jsonPath("$.data.followerCount").value(member1.getFollowerCount()))
                .andExpect(jsonPath("$.data.followingCount").value(member1.getFollowingCount()))
                .andExpect(jsonPath("$.data.getProjectInfoResponseList", hasSize(2)))

                .andExpect(jsonPath("$.data.getProjectInfoResponseList[0].id").value(1))
                .andExpect(jsonPath("$.data.getProjectInfoResponseList[0].projectName").value("project1"))
                .andExpect(jsonPath("$.data.getProjectInfoResponseList[0].description").value("description1"))

                .andExpect(jsonPath("$.data.getProjectInfoResponseList[1].id").value(2))
                .andExpect(jsonPath("$.data.getProjectInfoResponseList[1].projectName").value("project2"))
                .andExpect(jsonPath("$.data.getProjectInfoResponseList[1].description").value("description2"))

                .andDo(document("members/myPage/find/success",
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),

                                fieldWithPath("data.nickname").description("닉네임"),
                                fieldWithPath("data.introduction").description("본인 소개"),
                                fieldWithPath("data.followerCount").description("팔로워 수"),
                                fieldWithPath("data.followingCount").description("팔로잉 수"),
                                fieldWithPath("data.getProjectInfoResponseList").description("참여한 프로젝트 목록"),

                                fieldWithPath("data.getProjectInfoResponseList[].id").description("프로젝트 ID"),
                                fieldWithPath("data.getProjectInfoResponseList[].projectName").description("프로젝트 이름"),
                                fieldWithPath("data.getProjectInfoResponseList[].description").description("프로젝트 설명")
                        )));
    }
}
