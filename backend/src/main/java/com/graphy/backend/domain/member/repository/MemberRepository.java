package com.graphy.backend.domain.member.repository;

import com.graphy.backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findMemberByNicknameStartingWith(String nickname);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Member m " +
            "set m.followerCount = m.followerCount + 1 " +
            "where m.id = :toId")
    void increaseFollowerCount(Long toId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Member m " +
            "set m.followingCount = m.followingCount + 1 " +
            "where m.id = :fromId")
    void increaseFollowingCount(Long fromId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Member m " +
            "set m.followerCount = case m.followerCount when 0 then 0 "
            + "else (m.followerCount - 1) end where m.id = :toId")
    void decreaseFollowerCount(Long toId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Member m " +
            "set m.followingCount = case m.followingCount when 0 then 0 "
            + "else (m.followingCount - 1) end where m.id = :fromId")
    void decreaseFollowingCount(Long fromId);
}
