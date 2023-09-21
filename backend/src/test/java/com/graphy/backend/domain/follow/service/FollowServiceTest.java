package com.graphy.backend.domain.follow.service;

import com.graphy.backend.domain.follow.domain.Follow;
import com.graphy.backend.domain.follow.repository.FollowRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.global.error.exception.AlreadyExistException;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.test.MockTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest extends MockTest {
    @Mock
    FollowRepository followRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    FollowService followService;

    @Test
    @DisplayName("팔로우 신청 테스트")
    void followTest() throws Exception {
        //given
        Member fromMember = Member.builder().id(1L).build();
        Long toId = 2L;

        //when
        doNothing().when(memberRepository).increaseFollowingCount(fromMember.getId());
        doNothing().when(memberRepository).increaseFollowerCount(toId);
        followService.addFollow(toId, fromMember);

        //then
        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    @DisplayName("팔로잉 리스트 조회 테스트")
    void getFollowingListTest() throws Exception {
        //given
        Member fromMember = Member.builder().id(1L).build();
        GetMemberListResponse following1 = new GetMemberListResponse() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        GetMemberListResponse following2 = new GetMemberListResponse() {
            public Long getId() {
                return 3L;
            }
            public String getNickname() {
                return "memberB";
            }
        };
        List<GetMemberListResponse> followingList = Arrays.asList(following1, following2);

        //when
        when(followRepository.findFollowings(fromMember.getId())).thenReturn(followingList);
        List<GetMemberListResponse> result = followService.findFollowingList(fromMember);

        //then
        Assertions.assertThat(result.get(0).getId()).isEqualTo(2L);
        Assertions.assertThat(result.get(0).getNickname()).isEqualTo("memberA");
        Assertions.assertThat(result.get(1).getId()).isEqualTo(3L);
        Assertions.assertThat(result.get(1).getNickname()).isEqualTo("memberB");
    }

    @Test
    @DisplayName("팔로워 리스트 조회 테스트")
    void getFollowerListTest() throws Exception {
        //given
        Member toMember = Member.builder().id(1L).build();
        GetMemberListResponse follower1 = new GetMemberListResponse() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        GetMemberListResponse follower2 = new GetMemberListResponse() {
            public Long getId() {
                return 3L;
            }
            public String getNickname() {
                return "memberB";
            }
        };
        List<GetMemberListResponse> followerList = Arrays.asList(follower1, follower2);

        //when
        when(followRepository.findFollowers(toMember.getId())).thenReturn(followerList);
        List<GetMemberListResponse> result = followService.findFollowerList(toMember);

        //then
        Assertions.assertThat(result.get(0).getId()).isEqualTo(2L);
        Assertions.assertThat(result.get(0).getNickname()).isEqualTo("memberA");
        Assertions.assertThat(result.get(1).getId()).isEqualTo(3L);
        Assertions.assertThat(result.get(1).getNickname()).isEqualTo("memberB");
    }

    @Test
    @DisplayName("언팔로우 테스트")
    void unfollowTest() throws Exception {
        //given
        Long toId = 1L;
        Member fromMember = Member.builder().id(2L).build();
        Follow follow = Follow.builder().fromId(fromMember.getId()).toId(toId).build();

        //when
        when(followRepository.findByFromIdAndToId(fromMember.getId(), toId)).thenReturn(Optional.ofNullable(follow));
        doNothing().when(memberRepository).decreaseFollowingCount(fromMember.getId());
        doNothing().when(memberRepository).decreaseFollowerCount(toId);
        followService.removeFollow(toId, fromMember);

        //then
        verify(followRepository, times(1)).delete(follow);
    }

    @Test
    @DisplayName("존재하지 않는 팔로우 예외처리 테스트")
    void unfollowNotFoundTest() {
        // given
        Long toId = 1L;
        Member fromMember = Member.builder().id(2L).build();

        when(followRepository.findByFromIdAndToId(fromMember.getId(), toId))
                .thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(EmptyResultException.class, () -> {
            followService.removeFollow(toId, fromMember);
        });

        // then
        String exceptionMessage = exception.getMessage();

        assertTrue(exceptionMessage.equals("존재하지 않는 팔로우"));
    }

    @Test
    @DisplayName("팔로우 여부 체크 테스트")
    void followingCheckTest() throws Exception {
        // given
        when(followRepository.existsByFromIdAndToId(1L, 2L)).thenReturn(true);
        when(followRepository.existsByFromIdAndToId(3L, 4L)).thenReturn(false);

        // when & then
        Member loginUser = new Member();
        assertThatThrownBy(
                        () -> followService.checkFollowingAlready(1L, 2L))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining("이미 존재하는 팔로우");

        Assertions.assertThatCode(() -> followService.checkFollowingAlready(3L, 4L))
                .doesNotThrowAnyException();
    }
}
