package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.member.dto.MemberListDto;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Like;
import com.graphy.backend.domain.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByProjectAndMember(Project project, Member member);

    @Query(value = "select m.id, m.nickname " +
            "from Like l " +
            "inner join Member m on m.id = l.member_id " +
            "where l.project_id = :projectId", nativeQuery = true)
    List<MemberListDto> findLikedMembers(Long projectId);
}

