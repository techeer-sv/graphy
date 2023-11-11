package com.graphy.backend.domain.auth.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.global.error.exception.EmptyResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자의 이메일이 주어지면 UserDetails 객체를 반환한다")
    public void loadUserByUsernameTest() throws Exception {
        // given
        Member member = Member.builder()
                .email("graphy@gmail.com")
                .password("password")
                .build();
        // when
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        UserDetails actual = customUserDetailsService.loadUserByUsername(member.getEmail());

        // then
        assertThat(actual.getUsername()).isEqualTo(member.getEmail());
        assertThat(actual.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    @DisplayName("존재하지 않는 사용자의 이메일로 UserDetails 조회 시 예외가 발생한다")
    public void loadUserByUsernameNotExistMemberExceptionTest() throws Exception {
        // given
        String 존재하지_않는_사용자의_이메일 = "NotExistMemberEmail";

        // when, then
        assertThatThrownBy(
                () -> customUserDetailsService.loadUserByUsername(존재하지_않는_사용자의_이메일))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("존재하지 않는 사용자");
    }
}
