package com.graphy.backend.domain.follow.controller;

import com.graphy.backend.domain.member.dto.MemberListDto;
import com.graphy.backend.domain.follow.service.FollowService;
import com.graphy.backend.global.auth.jwt.TokenProvider;
import com.graphy.backend.global.auth.redis.repository.RefreshTokenRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    public void followTest() throws Exception {
        //given
        Long id = 1L;

        //when
        doNothing().when(followService).follow(id);

        //then
        mvc.perform(post(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andDo(document("follow-create",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("언팔로우 테스트")
    public void unfollowTest() throws Exception {
        //given
        Long id = 1L;

        //when
        doNothing().when(followService).unfollow(id);

        //then
        mvc.perform(delete(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andDo(document("follow-delete",
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("팔로잉 리스트 조회 테스트")
    public void getFollowingListTest() throws Exception {
        // given
        MemberListDto following1 = new MemberListDto() {
            public Long getId() {
                return 1L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        MemberListDto following2 = new MemberListDto() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberB";
            }
        };

        List<MemberListDto> followingList = Arrays.asList(following1, following2);

        // when
        given(followService.getFollowings()).willReturn(followingList);

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
    public void getFollowerListTest() throws Exception {
        // given
        MemberListDto follower1 = new MemberListDto() {
            public Long getId() {
                return 1L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        MemberListDto follower2 = new MemberListDto() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberB";
            }
        };

        List<MemberListDto> followerList = Arrays.asList(follower1, follower2);

        // when
        given(followService.getFollowers()).willReturn(followerList);

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
