package com.graphy.backend.domain.auth.service;

import com.graphy.backend.domain.auth.controller.JwtFilter;
import com.graphy.backend.domain.auth.domain.RefreshToken;
import com.graphy.backend.domain.auth.dto.request.LogoutRequest;
import com.graphy.backend.domain.auth.dto.response.GetTokenInfoResponse;
import com.graphy.backend.domain.auth.infra.TokenProvider;
import com.graphy.backend.domain.auth.repository.RefreshTokenRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.dto.request.SignUpMemberRequest;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest extends MockTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private MemberService memberService;
    @Mock
    private JwtFilter filter;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private Authentication authentication;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private Member member;
    @BeforeEach
    public void setup() {
        member = Member.builder()
                .id(1L)
                .email("email1@gmail.com")
                .nickname("name1")
                .introduction("introduction1")
                .followingCount(10)
                .followerCount(11)
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("회원가입을 한다")
    public void signUpTest() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.builder()
                .email("google@naver.kakao")
                .introduction("안녕 날 소개하지")
                .nickname("graphy")
                .build();

        String 암호화된_비밀번호 = "encodedPassword";

        // when
        doNothing().when(memberService).checkEmailDuplicate(request.getEmail());
        when(encoder.encode(request.getPassword())).thenReturn(암호화된_비밀번호);

        // then
        assertDoesNotThrow(() -> authService.signUp(request));
    }
    
    @Test
    @DisplayName("로그아웃을 한다")
    public void logoutTest() throws Exception {
        // given
        LogoutRequest request = LogoutRequest.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("token")
                .email(member.getEmail())
                .build();

        // when
        when(tokenProvider.validateToken(request.getAccessToken())).thenReturn(true);
        when(tokenProvider.getAuthentication(request.getAccessToken())).thenReturn(authentication);
        when(authentication.getName()).thenReturn(member.getEmail());
        when(refreshTokenRepository.findByEmail(member.getEmail())).thenReturn(refreshToken);
        when(tokenProvider.getExpiration(request.getAccessToken())).thenReturn(100000L);

        // then
        assertDoesNotThrow(() -> authService.logout(request));
    }

    @Test
    @DisplayName("로그아웃 시 토큰이 유효하지 않으면 예외가 발생한다")
    public void logoutInvalidTokenExceptionTest() {
        // given
        LogoutRequest request = LogoutRequest.builder()
                .accessToken("invalidAccessToken")
                .refreshToken("refreshToken")
                .build();

        // when
        when(tokenProvider.validateToken(request.getAccessToken())).thenReturn(false);

        // then
        assertThatThrownBy(
                () -> authService.logout(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 토큰입니다.");
    }

    @Test
    @DisplayName("토큰을 재발급한다")
    public void reissueTest() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        String requestToken = "accessToken";

        RefreshToken savedRefreshToken = RefreshToken.builder()
                .token("token")
                .email(member.getEmail())
                .build();

        GetTokenInfoResponse newToken = GetTokenInfoResponse.builder()
                .accessToken("newAccessToken")
                .refreshToken("newRefreshToken")
                .build();

        // when
        when(filter.resolveToken(any(HttpServletRequest.class))).thenReturn(requestToken);
        when(tokenProvider.validateToken(requestToken)).thenReturn(true);
        when(refreshTokenRepository.findByEmail(member.getEmail())).thenReturn(savedRefreshToken);
        when(tokenProvider.generateToken(savedRefreshToken.getToken(), savedRefreshToken.getAuthorities())).thenReturn(newToken);

        GetTokenInfoResponse actual = authService.reissue(request, member);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(newToken);
    }

    @Test
    @DisplayName("토큰 재발급 요청 시 리프래시 토큰이 존재하지 않으면 예외가 발생한다")
    public void reissueNotExistRefreshTokenExceptionTest() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        String requestToken = "accessToken";

        // when
        when(filter.resolveToken(any(HttpServletRequest.class))).thenReturn(requestToken);
        when(tokenProvider.validateToken(requestToken)).thenReturn(true);
        when(refreshTokenRepository.findByEmail(member.getEmail())).thenReturn(null);


        // then
        assertThatThrownBy(
                () -> authService.reissue(request, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 토큰입니다.");
    }
}
