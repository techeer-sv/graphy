package com.graphy.backend.domain.member.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.dto.response.GetMemberResponse;
import com.graphy.backend.domain.member.dto.response.GetMyPageResponse;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.dto.response.GetProjectInfoResponse;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.error.exception.AlreadyExistException;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest extends MockTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProjectService projectService;

    private Member member1;
    private Member member2;
    @BeforeEach
    public void setup() {
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
    @DisplayName("닉네임으로 사용자 목록을 조회한다")
    void findMemberListTest() {
        // given
        List<Member> memberList = Arrays.asList(member1, member2);
        
        // when
        when(memberRepository.findMemberByNicknameStartingWith(member1.getNickname()))
                .thenReturn(memberList);

        List<GetMemberResponse> actual = memberService.findMemberList(member1.getNickname());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(memberList);
    }

    @Test
    @DisplayName("닉네임으로 사용자 목록을 조회 시 일치하는 사용자가 없으면 빈 목록이 반환된다")
    void findMemberListEmptyListTest() {
        // given
        String 존재하지_않는_닉네임 = "emptyListNickname";

        // when
        when(memberRepository.findMemberByNicknameStartingWith(존재하지_않는_닉네임))
                .thenReturn(Collections.emptyList());

        List<GetMemberResponse> actual = memberService.findMemberList(존재하지_않는_닉네임);

        // then
        assertThat(actual).hasSize(0);
    }

    @Test
    @DisplayName("사용자 ID로 사용자를 조회한다")
    void findMemberByIdTest() {
        // when
        when(memberRepository.findById(member1.getId())).thenReturn(Optional.ofNullable(member1));
        Member actual = memberService.findMemberById(member1.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(member1);
    }

    @Test
    @DisplayName("사용자 ID로 사용자를 조회 시 사용자가 존재하지 않으면 예외가 발생한다")
    void findMemberByIdNotExistMemberExceptionTest() {
        // given
        Long 존재하지_않는_사용자_ID = 0L;

        // when, then
        assertThatThrownBy(
                () -> memberService.findMemberById(존재하지_않는_사용자_ID))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("존재하지 않는 사용자");
    }

    @Test
    @DisplayName("현재 로그인한 사용자를 상세 조회한다")
    void myPageTest() {
        // given
        GetProjectInfoResponse response1 = GetProjectInfoResponse.builder()
                .id(1L)
                .projectName("project1")
                .description("description1")
                .build();

        GetProjectInfoResponse response2 = GetProjectInfoResponse.builder()
                .id(2L)
                .projectName("project2")
                .description("description2")
                .build();

        List<GetProjectInfoResponse> responseList = Arrays.asList(response1, response2);

        // when
        when(projectService.findProjectInfoList(member1.getId())).thenReturn(responseList);
        GetMyPageResponse actual = projectService.myPage(member1);

        // then
        assertThat(actual.getNickname()).isEqualTo(member1.getNickname());
        assertThat(actual.getIntroduction()).isEqualTo(member1.getIntroduction());
        assertThat(actual.getFollowerCount()).isEqualTo(member1.getFollowerCount());
        assertThat(actual.getFollowingCount()).isEqualTo(member1.getFollowingCount());

        assertThat(actual.getGetProjectInfoResponseList())
                .usingRecursiveComparison()
                .isEqualTo(responseList);
    }

    @Test
    @DisplayName("이메일이 중복된 경우 예외가 발생한다")
    void checkEmailDuplicateTest() {
        // given
        String 중복된_이메일 = member1.getEmail();

        // when
        when(memberRepository.findByEmail(중복된_이메일)).thenReturn(Optional.ofNullable(member1));

        // then
        assertThatThrownBy(
                () -> memberService.checkEmailDuplicate(중복된_이메일))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining("이미 존재하는 이메일");
    }

    @Test
    @DisplayName("사용자를 저장한다")
    void addMemberTest() {
        // when, then
        assertDoesNotThrow(() -> memberService.addMember(member1));
    }
}
