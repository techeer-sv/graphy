package com.graphy.backend.global.auth.jwt;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().name());
        return new User(
                member.getEmail(),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    public Member getLoginUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.")
        );
    }
}