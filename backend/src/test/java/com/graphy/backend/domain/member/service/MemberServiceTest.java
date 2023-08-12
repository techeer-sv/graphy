package com.graphy.backend.domain.member.service;

import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.auth.jwt.CustomUserDetailsService;
import com.graphy.backend.global.auth.jwt.TokenProvider;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest extends MockTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private ProjectService projectService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private MemberService memberService;


}
