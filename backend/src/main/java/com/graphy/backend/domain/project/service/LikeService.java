package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.domain.Like;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.LikeRepository;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.graphy.backend.global.error.ErrorCode.MEMBER_NOT_EXIST;
import static com.graphy.backend.global.error.ErrorCode.PROJECT_DELETED_OR_NOT_EXIST;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    public void findLikedMember(Long projectId) {
        //TODO
    }

    public void likeProject(Long projectId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EmptyResultException(MEMBER_NOT_EXIST));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EmptyResultException(PROJECT_DELETED_OR_NOT_EXIST));

        Optional<Like> liked = likeRepository.findByProjectAndMember(project, member);

        liked.ifPresentOrElse(l -> {
            likeRepository.delete(l);
            project.updateLikeCount(-1);
        }, () -> {
            Like like = Like.builder()
                    .member(member)
                    .project(project).build();
            project.updateLikeCount(1);
            likeRepository.save(like);
        });

        projectRepository.save(project);
    }
}
