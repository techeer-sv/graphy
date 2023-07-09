package com.graphy.backend.domain.member.controller;

import com.graphy.backend.domain.member.dto.MemberInfo;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.global.auth.jwt.dto.TokenInfo;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.graphy.backend.domain.member.dto.MemberDto.*;

@Tag(name = "MemberController", description = "회원 API")
@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "login", description = "로그인")
    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginMemberRequest request) {
        return memberService.login(request);
    }

    @Operation(summary = "join", description = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(@Validated @RequestBody CreateMemberRequest request) {
        memberService.join(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_CREATE_SUCCESS));
    }

    @Operation(summary = "get member", description = "사용자 조회")
    @GetMapping()
    public ResponseEntity<ResultResponse> findMember(@RequestParam String nickname) {
        List<GetMemberResponse> result = memberService.findMember(nickname);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_CREATE_SUCCESS, result));
    }

    @Operation(summary = "myPage", description = "마이페이지")
    @GetMapping("/myPage")
    public ResponseEntity<ResultResponse> myPage(@AuthenticationPrincipal MemberInfo memberInfo) {
        GetMyPage result = memberService.myPage(memberInfo);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MYPAGE_GET_SUCCESS, result));
    }
}
