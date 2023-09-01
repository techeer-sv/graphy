package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.domain.project.domain.Like;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.LikeRepository;
import com.graphy.backend.domain.auth.service.CustomUserDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeService {

    private final LikeRepository likeRepository;

    private final MemberService memberService;

    private final ProjectService projectService;

    private final CustomUserDetailsService customUserDetailsService;

    public List<GetMemberListResponse> findLikedMemberList(Long projectId) {
        return likeRepository.findLikedMembers(projectId);
    }

    @Transactional
    public void likeProject(Long projectId) {
        Long memberId = customUserDetailsService.getLoginUser().getId();
        Member member = memberService.findMemberById(memberId);
        Project project = projectService.getProjectById(projectId);

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

        projectService.saveProject(project);
    }
}
