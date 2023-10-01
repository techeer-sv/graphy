package com.graphy.backend.domain.follow.service;

import com.graphy.backend.domain.follow.domain.Follow;
import com.graphy.backend.domain.follow.repository.FollowRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberListResponse;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.AlreadyExistException;
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

    public void addFollow(Long toId, Member loginUser) {
        Long fromId = loginUser.getId();
        checkFollowingAlready(loginUser.getId(), toId);
        Follow follow = Follow.builder().fromId(fromId).toId(toId).build();
        followRepository.save(follow);
        memberRepository.increaseFollowerCount(toId);
        memberRepository.increaseFollowingCount(fromId);
    }

    public void removeFollow(Long toId, Member loginUser) {
        Long fromId = loginUser.getId();
        Follow follow = followRepository.findByFromIdAndToId(fromId, toId).orElseThrow(
                () -> new EmptyResultException(ErrorCode.FOLLOW_NOT_EXIST)
        );
        followRepository.delete(follow);
        memberRepository.decreaseFollowerCount(toId);
        memberRepository.decreaseFollowingCount(fromId);
    }

    public List<GetMemberListResponse> findFollowerList(Member loginUser) {
        return followRepository.findFollowers(loginUser.getId());
    }

    public List<GetMemberListResponse> findFollowingList(Member loginUser) {
        return followRepository.findFollowings(loginUser.getId());
    }

    public void checkFollowAvailable(Long fromId, Long toId) {
        if (followRepository.existsByFromIdAndToId(fromId, toId)) {
            throw new AlreadyExistException(ErrorCode.FOLLOW_ALREADY_EXIST);
        }
        if (fromId.equals(toId)) {
            throw new AlreadyExistException(ErrorCode.FOLLOW_SELF);
        }
    }
}
