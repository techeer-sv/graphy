package com.graphy.backend.domain.member.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.response.GetMemberResponse;
import com.graphy.backend.domain.member.dto.response.GetMyPageResponse;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.dto.response.GetProjectInfoResponse;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.AlreadyExistException;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.graphy.backend.global.error.ErrorCode.MEMBER_NOT_EXIST;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProjectService projectService;


    public List<GetMemberResponse> findMemberList(String nickname) {
        List<Member> memberList = memberRepository.findMemberByNicknameStartingWith(nickname);
        return memberList.stream()
                .map(GetMemberResponse::from)
                .collect(Collectors.toList());
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(()
                -> new EmptyResultException(MEMBER_NOT_EXIST));
    }

    public GetMyPageResponse myPage(Member member) {
        List<GetProjectInfoResponse> projectInfoList = projectService.findProjectInfoList(member.getId());
        return GetMyPageResponse.of(member, projectInfoList);
    }

    public void checkEmailDuplicate(String email) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new AlreadyExistException(ErrorCode.MEMBER_ALREADY_EXIST);
    }

    public void addMember(Member member) {
        memberRepository.save(member);
    }
}
