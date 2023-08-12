package com.graphy.backend.domain.follow.service;

import com.graphy.backend.domain.follow.domain.Follow;
import com.graphy.backend.domain.member.dto.MemberListDto;
import com.graphy.backend.domain.follow.repository.FollowRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.global.auth.jwt.CustomUserDetailsService;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest extends MockTest {
    @Mock
    FollowRepository followRepository;
    @Mock
    CustomUserDetailsService customUserDetailsService;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    FollowService followService;

    @Test
    @DisplayName("팔로우 신청 테스트")
    public void followTest() throws Exception {
        //given
        Member fromMember = Member.builder().id(1L).build();
        Long toId = 2L;

        //when
        when(customUserDetailsService.getLoginUser()).thenReturn(fromMember);
        doNothing().when(memberRepository).increaseFollowingCount(fromMember.getId());
        doNothing().when(memberRepository).increaseFollowerCount(toId);
        followService.follow(toId);

        //then
        verify(followRepository, times(1)).save(any(Follow.class));
        verify(customUserDetailsService, times(1)).getLoginUser();
    }

    @Test
    @DisplayName("팔로잉 리스트 조회 테스트")
    public void getFollowingListTest() throws Exception {
        //given
        Member fromMember = Member.builder().id(1L).build();
        MemberListDto following1 = new MemberListDto() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        MemberListDto following2 = new MemberListDto() {
            public Long getId() {
                return 3L;
            }
            public String getNickname() {
                return "memberB";
            }
        };
        List<MemberListDto> followingList = Arrays.asList(following1, following2);

        //when
        when(customUserDetailsService.getLoginUser()).thenReturn(fromMember);
        when(followRepository.findFollowing(fromMember.getId())).thenReturn(followingList);
        List<MemberListDto> result = followService.getFollowings();

        //then
        Assertions.assertThat(result.get(0).getId()).isEqualTo(2L);
        Assertions.assertThat(result.get(0).getNickname()).isEqualTo("memberA");
        Assertions.assertThat(result.get(1).getId()).isEqualTo(3L);
        Assertions.assertThat(result.get(1).getNickname()).isEqualTo("memberB");
    }

    @Test
    @DisplayName("팔로워 리스트 조회 테스트")
    public void getFollowerListTest() throws Exception {
        //given
        Member toMember = Member.builder().id(1L).build();
        MemberListDto follower1 = new MemberListDto() {
            public Long getId() {
                return 2L;
            }
            public String getNickname() {
                return "memberA";
            }
        };

        MemberListDto follower2 = new MemberListDto() {
            public Long getId() {
                return 3L;
            }
            public String getNickname() {
                return "memberB";
            }
        };
        List<MemberListDto> followerList = Arrays.asList(follower1, follower2);

        //when
        when(customUserDetailsService.getLoginUser()).thenReturn(toMember);
        when(followRepository.findFollower(toMember.getId())).thenReturn(followerList);
        List<MemberListDto> result = followService.getFollowers();

        //then
        Assertions.assertThat(result.get(0).getId()).isEqualTo(2L);
        Assertions.assertThat(result.get(0).getNickname()).isEqualTo("memberA");
        Assertions.assertThat(result.get(1).getId()).isEqualTo(3L);
        Assertions.assertThat(result.get(1).getNickname()).isEqualTo("memberB");
    }

    @Test
    @DisplayName("언팔로우 테스트")
    public void unfollowTest() throws Exception {
        //given
        Long toId = 1L;
        Member fromMember = Member.builder().id(2L).build();
        Follow follow = Follow.builder().fromId(fromMember.getId()).toId(toId).build();

        //when
        when(customUserDetailsService.getLoginUser()).thenReturn(fromMember);
        when(followRepository.findByFromIdAndToId(fromMember.getId(), toId)).thenReturn(Optional.ofNullable(follow));
        doNothing().when(memberRepository).decreaseFollowingCount(fromMember.getId());
        doNothing().when(memberRepository).decreaseFollowerCount(toId);
        followService.unfollow(toId);

        //then
        verify(followRepository, times(1)).delete(follow);
    }

    @Test
    @DisplayName("존재하지 않는 팔로우 예외처리 테스트")
    public void unfollowNotFoundTest() {
        // given
        Long toId = 1L;
        Member fromMember = Member.builder().id(2L).build();

        when(customUserDetailsService.getLoginUser()).thenReturn(fromMember);
        when(followRepository.findByFromIdAndToId(fromMember.getId(), toId))
                .thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(EmptyResultException.class, () -> {
            followService.unfollow(toId);
        });

        // then
        String exceptionMessage = exception.getMessage();

        assertTrue(exceptionMessage.equals("존재하지 않는 팔로우"));
    }

    @Test
    @DisplayName("팔로우 여부 체크 테스트")
    public void followingCheckTest() throws Exception {
        //when
        when(followRepository.existsByFromIdAndToId(1L, 2L)).thenReturn(true);
        when(followRepository.existsByFromIdAndToId(3L, 4L)).thenReturn(false);

        // then
        assertThrows(AlreadyExistException.class, () -> {
            ReflectionTestUtils.invokeMethod(followService, "followingCheck", 1L, 2L);
        });

        assertDoesNotThrow(() -> {
            ReflectionTestUtils.invokeMethod(followService, "followingCheck", 3L, 4L);
        });
    }
}
