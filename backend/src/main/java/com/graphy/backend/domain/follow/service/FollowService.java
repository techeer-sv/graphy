package com.graphy.backend.domain.follow.service;

import com.graphy.backend.domain.follow.domain.Follow;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.MemberListDto;
import com.graphy.backend.domain.follow.repository.FollowRepository;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.global.auth.jwt.CustomUserDetailsService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.AlreadyFollowingException;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public void follow(Long toId) {
        Long fromId = customUserDetailsService.getLoginUser().getId();
        followingCheck(fromId, toId);
        Follow follow = Follow.builder().fromId(fromId).toId(toId).build();
        followRepository.save(follow);
        memberRepository.increaseFollowerCount(toId);
        memberRepository.increaseFollowingCount(fromId);
    }

    public void unfollow(Long toId) {
        Long fromId = customUserDetailsService.getLoginUser().getId();
        Follow follow = followRepository.findByFromIdAndToId(fromId, toId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.FOLLOW_NOT_EXIST)
        );
        followRepository.delete(follow);
        memberRepository.decreaseFollowerCount(toId);
        memberRepository.decreaseFollowingCount(fromId);
    }

    public List<MemberListDto> getFollowers() {
        Long id = customUserDetailsService.getLoginUser().getId();
        return followRepository.findFollower(id);
    }

    public List<MemberListDto> getFollowings() {
        Long id = customUserDetailsService.getLoginUser().getId();
        return followRepository.findFollowing(id);
    }

    private void followingCheck(Long fromId, Long toId) {
        if (followRepository.existsByFromIdAndToId(fromId, toId)) {
            throw new AlreadyFollowingException(ErrorCode.FOLLOW_ALREADY_EXIST);
        }
    }
}
