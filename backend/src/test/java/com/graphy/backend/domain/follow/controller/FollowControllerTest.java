package com.graphy.backend.domain.follow.controller;

import com.graphy.backend.domain.auth.util.MemberInfo;
import com.graphy.backend.domain.follow.service.FollowService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.test.MockApiTest;
import com.graphy.backend.test.util.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.graphy.backend.domain.member.domain.Role.ROLE_USER;
import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentRequest;
import static com.graphy.backend.test.config.ApiDocumentUtil.getDocumentResponse;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(FollowController.class)
@ExtendWith(RestDocumentationExtension.class)
class FollowControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    FollowService followService;

    MemberInfo memberInfo;
    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
        memberInfo = new MemberInfo(Member.builder().id(1L).email("test@gmail.com").password("password").role(ROLE_USER).build());
    }

    private static final String BASE_URL = "/api/v1/follow";

    @Test
    @DisplayName("팔로우 신청 테스트")
    void followTest() throws Exception {
        //given
        Long id = 1L;

        // when & then
        mvc.perform(post(BASE_URL + "/{id}", id).principal(new TestingAuthenticationToken("testEmail", "testPassword")))
                .andExpect(status().isCreated())
                .andDo(document("follow/add/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("팔로우할 memberID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @DisplayName("언팔로우 테스트")
    void unfollowTest() throws Exception {
        //given
        Long id = 1L;

        //then
        mvc.perform(RestDocumentationRequestBuilders.delete(BASE_URL + "/{id}", id).principal(new TestingAuthenticationToken("testEmail", "testPassword")))
                .andExpect(status().isNoContent())
                .andDo(document("follow/remove/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("언팔로우할 memberID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터")
                        )));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("팔로잉 리스트 조회 테스트")
    void getFollowingListTest() throws Exception {
        // given
        GetMemberListResponse following1 = new GetMemberListResponse() {
            public Long getId() {
                return 1L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        GetMemberListResponse following2 = new GetMemberListResponse() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberB";
            }
        };

        List<GetMemberListResponse> followingList = Arrays.asList(following1, following2);

        // when
        given(followService.findFollowingList(any(Member.class))).willReturn(followingList);

        //then
        mvc.perform(get(BASE_URL + "/following"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").exists())
                .andExpect(jsonPath("$.data[0].nickname").value("memberA"))
                .andExpect(jsonPath("$.data[1].id").exists())
                .andExpect(jsonPath("$.data[1].nickname").value("memberB"))
                .andDo(document("follow/followingList/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data[].id").description("팔로잉하는 사용자의 ID"),
                                fieldWithPath("data[].nickname").description("팔로잉하는 사용자의 nickname")
                        )));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("팔로워 리스트 조회 테스트")
    void getFollowerListTest() throws Exception {
        // given
        GetMemberListResponse follower1 = new GetMemberListResponse() {
            public Long getId() {
                return 1L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        GetMemberListResponse follower2 = new GetMemberListResponse() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberB";
            }
        };

        List<GetMemberListResponse> followerList = Arrays.asList(follower1, follower2);

        // when
        given(followService.findFollowerList(any(Member.class))).willReturn(followerList);

        //then
        mvc.perform(get(BASE_URL + "/follower").principal(new TestingAuthenticationToken("testEmail", "testPassword")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").exists())
                .andExpect(jsonPath("$.data[0].nickname").value("memberA"))
                .andExpect(jsonPath("$.data[1].id").exists())
                .andExpect(jsonPath("$.data[1].nickname").value("memberB"))
                .andDo(document("follow/followingList/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data[].id").description("팔로워 사용자의 ID"),
                                fieldWithPath("data[].nickname").description("팔로워 사용자의 nickname")
                                )));
    }
}
