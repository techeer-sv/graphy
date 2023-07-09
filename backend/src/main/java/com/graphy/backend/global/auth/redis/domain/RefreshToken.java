package com.graphy.backend.global.auth.redis.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RedisHash(value = "RT")
@Getter
@Builder
public class RefreshToken {
    @Id
    private String token;
    @Indexed
    private String email;
    private String ip;
    @TimeToLive
    private Long expiration = 14L * 24L * 60L * 60L; // 2주
    private Collection<? extends GrantedAuthority> authorities;

    public void updateToken(String token) {
        this.token = token;
    }
}
