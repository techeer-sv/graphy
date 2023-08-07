package com.graphy.backend.domain.member.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.auth.jwt.CustomUserDetailsService;
import com.graphy.backend.global.auth.jwt.JwtFilter;
import com.graphy.backend.global.auth.jwt.TokenProvider;
import com.graphy.backend.global.auth.redis.domain.RefreshToken;
import com.graphy.backend.global.auth.redis.repository.RefreshTokenRepository;
import com.graphy.backend.global.common.Helper;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.AlreadyExistException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Ref;
import java.util.List;
import java.util.stream.Collectors;

import static com.graphy.backend.domain.member.dto.MemberDto.*;
import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static com.graphy.backend.global.auth.jwt.dto.TokenDto.*;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final ProjectService projectService;
    private final JwtFilter filter;
    private final PasswordEncoder encoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public void join(CreateMemberRequest request) {
        if (checkEmailDuplicate(request.getEmail())) {
            String encodedPassword = encoder.encode(request.getPassword());
            Member member = request.toEntity(encodedPassword);
            memberRepository.save(member);
        } else {
            throw new AlreadyExistException(ErrorCode.MEMBER_ALREADY_EXIST);
        }
    }

    public TokenInfo login(HttpServletRequest request,
                                    LoginMemberRequest dto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        TokenInfo token = tokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token.getRefreshToken())
                .email(dto.getEmail())
                .authorities(authentication.getAuthorities())
                .ip(Helper.getClientIp(request))
                .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public void logout(LogoutRequest request){
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!tokenProvider.validateToken(request.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User email을 가져온다
        String email = tokenProvider.getAuthentication(request.getAccessToken()).getName();

        // Redis에서 해당 User email로 저장된 Refresh Token 조회
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email);

        // Refresh Token이 존재하는 경우 해당 토큰 삭제
        if (refreshToken!=null){
            refreshTokenRepository.delete(refreshToken);
        }

        // Access Token 유효시간을 blackList에 저장
        Long expiration = tokenProvider.getExpiration(request.getAccessToken()) / 1000;

        RefreshToken token = RefreshToken.builder()
                .token(request.getAccessToken())
                .expiration(expiration)
                .build();

        refreshTokenRepository.save(token);
    }

    public TokenInfo reissue(HttpServletRequest request) {
        //1. Request Header 에서 JWT Token 추출
        String requestToken = filter.resolveToken(request);
        Member member = getLoginMember();

        //2. validateToken 메서드로 토큰 유효성 검사
        if (requestToken != null && tokenProvider.validateToken(requestToken)) {

            //3. 저장된 refresh token 찾기
            RefreshToken savedRefreshToken = refreshTokenRepository.findByEmail(member.getEmail());
            if (savedRefreshToken != null) {

                //4. 최초 로그인한 ip 와 같은지 확인
                String currentIpAddress = Helper.getClientIp(request);
                if (savedRefreshToken.getIp().equals(currentIpAddress)) {

                    // 5. Redis 에 저장된 RefreshToken 정보를 기반으로 JWT Token 생성
                    TokenInfo newToken = tokenProvider.generateToken(savedRefreshToken.getToken(), savedRefreshToken.getAuthorities());

                    // 6. Redis RefreshToken update
                    RefreshToken newRefreshToken = RefreshToken.builder()
                            .token(newToken.getRefreshToken())
                            .email(member.getEmail())
                            .ip(savedRefreshToken.getIp())
                            .authorities(savedRefreshToken.getAuthorities())
                            .build();

                    refreshTokenRepository.save(newRefreshToken);
                    savedRefreshToken.updateToken(newRefreshToken.getToken());
                    return newToken;
                }
            }
        }
        return null;
    }


    public List<GetMemberResponse> findMember(String nickname) {
        List<Member> memberList = memberRepository.findMemberByNicknameStartingWith(nickname);
        return memberList.stream()
                .map(GetMemberResponse::toDto)
                .collect(Collectors.toList());
    }

    public GetMyPageResponse myPage(Member member) {
        List<GetProjectInfoResponse> projectInfoList = projectService.getProjectInfoList(member.getId());
        return GetMyPageResponse.from(member, projectInfoList);
    }

    private Member getLoginMember() {
        Member member = customUserDetailsService.getLoginUser();
        return member;
    }

    public boolean checkEmailDuplicate(String email) {
        if (memberRepository.findByEmail(email).isEmpty()) return true;
        return false;
    }
}
