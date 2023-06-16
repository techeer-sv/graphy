package com.graphy.backend.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
@Transactional
public class MemberService {
}
