package com.graphy.backend.domain.follow.repository;

import com.graphy.backend.domain.follow.domain.Follow;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFromIdAndToId(Long fromId, Long toId);
    Optional<Follow> findByFromIdAndToId(Long fromId, Long toId);

    /**
     * TODO
     * 아래 두 메서드 QueryDSL로 리팩토링 부탁드립니다!
     */
    @Query(value = "select m.id, m.nickname " +
            "from Follow f " +
            "inner join Member m on m.id = f.from_id " +
            "where f.to_id = :toId", nativeQuery = true)
    List<GetMemberListResponse> findFollower(Long toId);

    @Query(value = "select m.id, m.nickname " +
            "from Follow f " +
            "inner join Member m on m.id = f.to_id " +
            "where f.from_id = :fromId", nativeQuery = true)
    List<GetMemberListResponse> findFollowing(Long fromId);
}
