package com.graphy.backend.domain.member.controller;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberResponse;
import com.graphy.backend.domain.member.dto.response.GetMyPageResponse;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.auth.util.annotation.CurrentUser;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "MemberController", description = "회원 API")
@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "get member", description = "사용자 조회")
    @GetMapping()
    public ResponseEntity<ResultResponse> memberList(@RequestParam String nickname) {
        List<GetMemberResponse> result = memberService.findMemberList(nickname);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_GET_SUCCESS, result));
    }

    @Operation(summary = "myPage", description = "마이페이지")
    @GetMapping("/mypage")
    public ResponseEntity<ResultResponse> myPage(@CurrentUser Member member) {
        GetMyPageResponse result = memberService.myPage(member);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MYPAGE_GET_SUCCESS, result));
    }
}
