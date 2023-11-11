package com.graphy.backend.test.security;

import com.graphy.backend.domain.auth.util.MemberInfo;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.test.util.WithMockCustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

        @Override
        public SecurityContext createSecurityContext(WithMockCustomUser withMockCustomUser) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add((GrantedAuthority) () -> "ROLE_USER");
            Member member = Member.builder().id(1L).email("testEamil").password("testPassword").build();
            MemberInfo memberInfo = new MemberInfo(member);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(memberInfo, "testPassword", grantedAuthorities);
            context.setAuthentication(authentication);
            return context;
        }
}
