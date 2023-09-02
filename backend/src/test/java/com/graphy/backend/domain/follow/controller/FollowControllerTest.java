package com.graphy.backend.domain.follow.controller;

import com.graphy.backend.domain.auth.infra.TokenProvider;
import com.graphy.backend.domain.auth.repository.RefreshTokenRepository;
import com.graphy.backend.domain.follow.service.FollowService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(FollowController.class)
@ExtendWith(RestDocumentationExtension.class)
public class FollowControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    FollowService followService;
    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
    }

    private static String baseUrl = "/api/v1/follow";

    @Test
    @DisplayName("팔로우 신청 테스트")
    void followTest() throws Exception {
        //given
        Long id = 1L;

        //when
        doNothing().when(followService).addFollow(id, any(Member.class));

        //then
        mvc.perform(post(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andDo(document("follow-create",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("언팔로우 테스트")
    void unfollowTest() throws Exception {
        //given
        Long id = 1L;

        //when
        doNothing().when(followService).removeFollow(id, any(Member.class));

        //then
        mvc.perform(delete(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andDo(document("follow-delete",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
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
        mvc.perform(get(baseUrl + "/following"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").exists())
                .andExpect(jsonPath("$.data[0].nickname").value("memberA"))
                .andExpect(jsonPath("$.data[1].id").exists())
                .andExpect(jsonPath("$.data[1].nickname").value("memberB"))
                .andDo(document("followingList-get",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
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
        mvc.perform(get(baseUrl + "/follower"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").exists())
                .andExpect(jsonPath("$.data[0].nickname").value("memberA"))
                .andExpect(jsonPath("$.data[1].id").exists())
                .andExpect(jsonPath("$.data[1].nickname").value("memberB"))
                .andDo(document("followerList-get",
                        preprocessResponse(prettyPrint()))
                );
    }
}
