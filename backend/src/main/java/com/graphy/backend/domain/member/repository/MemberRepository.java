package com.graphy.backend.domain.member.repository;

import com.graphy.backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    Optional<Member> findByEmail(String email);
    List<Member> findMemberByNicknameStartingWith(String nickname);

    Optional<Member> findFirstByNickname(String nickname);
}
