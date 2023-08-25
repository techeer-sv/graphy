package com.graphy.backend.domain.auth.controller;

import com.graphy.backend.domain.auth.dto.request.LogoutRequest;
import com.graphy.backend.domain.auth.dto.response.GetTokenInfoResponse;
import com.graphy.backend.domain.auth.service.AuthService;
import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.request.SignInMemberRequest;
import com.graphy.backend.domain.member.dto.request.SignUpMemberRequest;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Tag(name = "AuthController", description = "인증 인가 API")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "signup", description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signUp(@Validated @RequestBody SignUpMemberRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_CREATE_SUCCESS));
    }
    
    @Operation(summary = "signin", description = "로그인")
    @PostMapping("/signin")
    public GetTokenInfoResponse signIn(@Validated @RequestBody SignInMemberRequest dto) {
        return authService.signIn(dto);
    }

    @Operation(summary = "logout", description = "로그아웃")
    @PostMapping(value = "/logout")
    public ResponseEntity<ResultResponse> logout(@RequestBody LogoutRequest dto) {
        authService.logout(dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_LOGOUT_SUCCESS));
    }
    
    @Operation(summary = "reIssue", description = "토큰 재발급")
    @PostMapping(value = "/reissue")
    public GetTokenInfoResponse reissue(HttpServletRequest request,
                                        @CurrentUser Member member) {
        return authService.reissue(request, member);
    }
}
