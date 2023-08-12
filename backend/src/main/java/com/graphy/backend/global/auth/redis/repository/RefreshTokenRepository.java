package com.graphy.backend.global.auth.redis.repository;

import com.graphy.backend.global.auth.redis.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    RefreshToken findByEmail(String email);

}
