package com.graphy.backend.domain.auth.controller;

import com.graphy.backend.domain.auth.dto.request.LogoutRequest;
import com.graphy.backend.domain.auth.dto.response.GetTokenInfoResponse;
import com.graphy.backend.domain.auth.service.AuthService;
import com.graphy.backend.domain.member.dto.request.SignInMemberRequest;
import com.graphy.backend.domain.member.dto.request.SignUpMemberRequest;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.AUTH_SIGNUP_SUCCESS));
    }
    
    @Operation(summary = "signin", description = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<ResultResponse> signIn(@Validated @RequestBody SignInMemberRequest dto) {
        GetTokenInfoResponse response = authService.signIn(dto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.AUTH_SIGNIN_SUCCESS, response));
    }

    @Operation(summary = "logout", description = "로그아웃")
    @PostMapping(value = "/logout")
    public ResponseEntity<ResultResponse> logout(@Validated @RequestBody LogoutRequest dto) {
        authService.logout(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResultResponse.of(ResultCode.AUTH_LOGOUT_SUCCESS));
    }
    
    @Operation(summary = "reIssue", description = "토큰 재발급")
    @PostMapping(value = "/reissue")
    public ResponseEntity<ResultResponse> reissue(HttpServletRequest request) {
        GetTokenInfoResponse response = authService.reissue(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.AUTH_REISSUE_SUCCESS, response));
    }
}
