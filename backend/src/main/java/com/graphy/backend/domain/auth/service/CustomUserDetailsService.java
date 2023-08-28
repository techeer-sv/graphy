package com.graphy.backend.domain.auth.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.auth.util.MemberInfo;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new EmptyResultException(ErrorCode.MEMBER_NOT_EXIST));
    }

    private UserDetails createUserDetails(Member member) {
        return new MemberInfo(member);
    }

    /**
     * TODO
     *  Follow, Like 도메인에 사용되는 메서드입니다.
     *  해당 도메인을 @CurrentUser로 리팩토링 후에 제거 부탁드립니다.
     */
    public Member getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new EmptyResultException(ErrorCode.MEMBER_NOT_EXIST)
        );
    }
}