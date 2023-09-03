package com.graphy.backend.domain.follow.repository;

import com.graphy.backend.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowCustomRepository {
    boolean existsByFromIdAndToId(Long fromId, Long toId);
    Optional<Follow> findByFromIdAndToId(Long fromId, Long toId);
}
