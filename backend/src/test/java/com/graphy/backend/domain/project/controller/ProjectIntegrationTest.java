package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.common.PageRequest;
import com.graphy.backend.test.config.TestProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
@ActiveProfiles(TestProfile.TEST)
@SpringBootTest
@Transactional
class ProjectIntegrationTest {
    @Autowired private ProjectRepository projectRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("프로젝트 전체 조회 통합 테스트")
    public void getProjectsTest() throws Exception {
        //given
        Member member = Member.builder().email("test").nickname("nick").password("1234").id(1L).build();
        memberRepository.save(member);
        Project project1 = Project.builder().id(1L).projectName("test").content("con").member(member).build();
        Project project2 = Project.builder().id(2L).projectName("test").content("con").member(member).build();
        Project project3 = Project.builder().id(3L).projectName("test").content("con").member(member).build();

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);


        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(0);
        pageRequest.setSize(3);
        Pageable pageable = pageRequest.of();

        List<Project> result = projectRepository.searchProjectsWith(pageable, "test", "con")
                .stream()
                .collect(Collectors.toList());

        assertAll(
                () -> Assertions.assertThat(result.get(0).getProjectName()).isEqualTo(project1.getProjectName()),
                () -> Assertions.assertThat(result.get(0).getContent()).isEqualTo(project1.getContent()),
                () -> Assertions.assertThat(result.get(1).getProjectName()).isEqualTo(project2.getProjectName()),
                () -> Assertions.assertThat(result.get(1).getContent()).isEqualTo(project2.getContent()),
                () -> Assertions.assertThat(result.get(2).getProjectName()).isEqualTo(project3.getProjectName()),
                () -> Assertions.assertThat(result.get(2).getContent()).isEqualTo(project3.getContent())
        );
    }
}