package com.graphy.backend.domain.project.repository;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Like;
import com.graphy.backend.domain.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByProjectAndMember(Project project, Member member);
}

