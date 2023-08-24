package com.graphy.backend.domain.auth.repository;

import com.graphy.backend.domain.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    RefreshToken findByEmail(String email);

}
