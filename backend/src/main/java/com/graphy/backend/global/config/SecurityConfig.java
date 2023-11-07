package com.graphy.backend.global.config;

import com.graphy.backend.domain.auth.controller.JwtFilter;
import com.graphy.backend.domain.auth.infra.TokenProvider;
import com.graphy.backend.domain.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()

                .authorizeRequests()
                .antMatchers(
                        "/api/v1/auth/**",

                        "/api/v1/projects",
                        "/api/v1/projects/rank",
                        "/api/v1/projects/{projectId}",

                        "/api/v1/comments/{commentId}",

                        "/api/v1/recruitments",
                        "/api/v1/recruitments/{recruitmentId}",

                        "/api/v1/members/**",

                        "/swagger-ui/**"
                ).permitAll()
                .antMatchers("/api/v1/**").hasRole("USER")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(new JwtFilter(tokenProvider, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
