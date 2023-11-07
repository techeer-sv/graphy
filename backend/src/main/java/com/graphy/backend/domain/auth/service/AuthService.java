package com.graphy.backend.domain.auth.service;

import com.graphy.backend.domain.auth.controller.JwtFilter;
import com.graphy.backend.domain.auth.domain.RefreshToken;
import com.graphy.backend.domain.auth.dto.request.LogoutRequest;
import com.graphy.backend.domain.auth.dto.response.GetTokenInfoResponse;
import com.graphy.backend.domain.auth.infra.TokenProvider;
import com.graphy.backend.domain.auth.repository.RefreshTokenRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.request.SignInMemberRequest;
import com.graphy.backend.domain.member.dto.request.SignUpMemberRequest;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.InvalidTokenException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthService {
    private final JwtFilter filter;
    private final PasswordEncoder encoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberService memberService;
    public void signUp(SignUpMemberRequest request) {
        memberService.checkEmailDuplicate(request.getEmail());

        String encodedPassword = encoder.encode(request.getPassword());
        Member member = request.toEntity(encodedPassword);
        memberService.addMember(member);
    }

    public GetTokenInfoResponse signIn(SignInMemberRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        GetTokenInfoResponse token = tokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token.getRefreshToken())
                .email(request.getEmail())
                .authorities(authentication.getAuthorities())
                .build();

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public void logout(LogoutRequest request){
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!tokenProvider.validateToken(request.getAccessToken()))
            throw new InvalidTokenException(ErrorCode.INPUT_INVALID_TOKEN);

        // Access Token에서 User email을 가져온다
        String email = tokenProvider.getAuthentication(request.getAccessToken()).getName();

        // Redis에서 해당 User email로 저장된 Refresh Token 조회
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email);

        // Refresh Token이 존재하는 경우 해당 토큰 삭제
        if (refreshToken!=null) refreshTokenRepository.delete(refreshToken);

        // Access Token 유효시간을 blackList에 저장
        Long expiration = tokenProvider.getExpiration(request.getAccessToken()) / 1000;

        RefreshToken token = RefreshToken.builder()
                .token(request.getAccessToken())
                .expiration(expiration)
                .build();

        refreshTokenRepository.save(token);
    }


    public GetTokenInfoResponse reissue(HttpServletRequest request) {
        String requestToken = filter.resolveToken(request);
        tokenProvider.validateToken(requestToken);

        String email = tokenProvider.getEmailInAuthentication(requestToken);

        RefreshToken savedRefreshToken = refreshTokenRepository.findByEmail(email);
        if (savedRefreshToken == null) throw new InvalidTokenException(ErrorCode.TOKEN_NOT_EXIST);

        GetTokenInfoResponse newToken = tokenProvider.generateToken(savedRefreshToken.getToken(), savedRefreshToken.getAuthorities());
        updateRefreshToken(email, savedRefreshToken, newToken);

        return newToken;
    }

    private void updateRefreshToken(String email,
                                    RefreshToken savedRefreshToken,
                                    GetTokenInfoResponse newToken) {

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(newToken.getRefreshToken())
                .email(email)
                .authorities(savedRefreshToken.getAuthorities())
                .build();

        refreshTokenRepository.save(newRefreshToken);
        savedRefreshToken.updateToken(newRefreshToken.getToken());
    }
}
