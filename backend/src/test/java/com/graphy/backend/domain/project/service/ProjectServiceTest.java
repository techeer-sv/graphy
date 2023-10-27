package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.auth.service.CustomUserDetailsService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.dto.response.GetMyPageResponse;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.ProjectTags;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.dto.request.CreateProjectRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectsRequest;
import com.graphy.backend.domain.project.dto.request.UpdateProjectRequest;
import com.graphy.backend.domain.project.dto.response.CreateProjectResponse;
import com.graphy.backend.domain.project.dto.response.GetProjectInfoResponse;
import com.graphy.backend.domain.project.dto.response.GetProjectResponse;
import com.graphy.backend.domain.project.dto.response.UpdateProjectResponse;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.common.dto.PageRequest;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest extends MockTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectTagService projectTagService;

    @Mock
    private TagService tagService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    HashOperations hashOperations;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("프로젝트 수정 테스트")
    void updateProject() throws Exception {
        //given
        Project project = Project.builder()
                .id(1L)
                .projectTags(new ProjectTags())
                .projectName("beforeUpdate")
                .description("des")
                .thumbNail("thumb")
                .content("content")
                .build();

        List<String> techTags = new ArrayList<>(Arrays.asList("Spring", "Django"));

        UpdateProjectRequest request = UpdateProjectRequest.builder()
                .projectName("afterUpdate")
                .description("des")
                .thumbNail("thumb")
                .content("content")
                .techTags(techTags)
                .build();

        Tag tag1 = Tag.builder().tech("Vue").build();
        Tag tag2 = Tag.builder().tech("Java").build();

        //when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(tagService.findTagListByName(techTags)).thenReturn(new Tags(List.of(tag1, tag2)));
        when(redisRankingTemplate.opsForHash()).thenReturn(hashOperations);

        UpdateProjectResponse result = projectService.modifyProject(project.getId(), request);

        //then
        assertThat(result.getProjectName()).isEqualTo("afterUpdate"); // 수정된 부분
        assertThat(result.getDescription()).isEqualTo(project.getDescription());
        assertThat(result.getThumbNail()).isEqualTo(project.getThumbNail());
        assertThat(result.getTechTags()).isEqualTo(new ArrayList<>(Arrays.asList("Vue", "Java")));
    }

    @Test
    @DisplayName("프로젝트 생성 테스트")
    void createProject() throws Exception {
        //given
        Member member = Member.builder().email("graphy").id(1L).build();
        List<String> techTags = new ArrayList<>(Arrays.asList("Spring", "Django"));

        Project project = Project.builder()
                .id(1L)
                .projectTags(new ProjectTags())
                .projectName("testProject")
                .build();

        CreateProjectRequest request = CreateProjectRequest.builder()
                .projectName("testProject")
                .techTags(techTags).
                build();

        //when
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(tagService.findTagListByName(techTags)).thenReturn(new Tags(List.of(
                Tag.builder().tech("Spring").build(),
                Tag.builder().tech("Django").build()
                )));
        CreateProjectResponse result = projectService.addProject(request, member);

        //then
        assertThat(result.getProjectId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("프로젝트 리스트 조회")
    void getProjects() throws Exception {
        //given
        GetProjectsRequest request = GetProjectsRequest.builder().projectName("name").build();

        Member member = Member.builder()
                .id(1L)
                .email("graphy@gmail.com")
                .nickname("name")
                .role(Role.ROLE_USER)
                .build();

        Project project1 = Project.builder().projectName("test1").member(member).projectTags(new ProjectTags()).build();
        Project project2 = Project.builder().projectName("test2").member(member).projectTags(new ProjectTags()).build();

        GetProjectResponse response1 = GetProjectResponse.builder().projectName(project1.getProjectName()).build();
        GetProjectResponse response2 = GetProjectResponse.builder().projectName(project2.getProjectName()).build();

        PageRequest pageRequest = new PageRequest();
        pageRequest.setSize(2);
        pageRequest.setPage(0);
        Pageable pageable = pageRequest.of();

        List<Project> projectList = new ArrayList<>(Arrays.asList(project1, project2));
        Page<Project> projects = new PageImpl<>(projectList);

        List<GetProjectResponse> responseList = new ArrayList<>(Arrays.asList(response1, response2));
        Page<GetProjectResponse> responses = new PageImpl<>(responseList);


        //when
        when(projectRepository.searchProjectsWith(pageable, request.getProjectName(), request.getContent()))
                .thenReturn(projects);

        List<GetProjectResponse> result = projectService.findProjectList(request, pageable);

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProjectName()).isEqualTo("test1");
        assertThat(result.get(1).getProjectName()).isEqualTo("test2");
    }

    @Test
    @DisplayName("프로젝트 삭제")
    void deleteProject() throws Exception {
        //when
        when(redisRankingTemplate.opsForHash()).thenReturn(hashOperations);
        projectService.removeProject(1L);

        //then
        verify(projectRepository).deleteById(1L);
    }
    @Test
    @DisplayName("현재 로그인한 사용자를 상세 조회한다")
    void myPageTest() {
        // given
        Member member1 = Member.builder()
                .id(1L)
                .email("email1@gmail.com")
                .nickname("name1")
                .introduction("introduction1")
                .followingCount(10)
                .followerCount(11)
                .role(Role.ROLE_USER)
                .build();

        Project project = Project.builder()
                .id(1L)
                .projectTags(new ProjectTags())
                .projectName("project1")
                .description("description1")
                .thumbNail("thumb")
                .content("content1")
                .build();

        Project project2 = Project.builder()
                .id(2L)
                .projectTags(new ProjectTags())
                .projectName("project2")
                .description("description2")
                .thumbNail("thumb")
                .content("content2")
                .build();

        GetProjectInfoResponse response1 = GetProjectInfoResponse.builder()
                .id(1L)
                .projectName("project1")
                .content("content1")
                .build();

        GetProjectInfoResponse response2 = GetProjectInfoResponse.builder()
                .id(2L)
                .projectName("project2")
                .content("content2")
                .build();

        List<GetProjectInfoResponse> responseList = Arrays.asList(response1, response2);

        // when
        when(projectRepository.findByMemberId(member1.getId())).thenReturn(List.of(project, project2));
        GetMyPageResponse actual = projectService.myPage(member1);

        // then
        assertThat(actual.getNickname()).isEqualTo(member1.getNickname());
        assertThat(actual.getIntroduction()).isEqualTo(member1.getIntroduction());
        assertThat(actual.getFollowerCount()).isEqualTo(member1.getFollowerCount());
        assertThat(actual.getFollowingCount()).isEqualTo(member1.getFollowingCount());

        assertThat(actual.getGetProjectInfoResponseList())
                .usingRecursiveComparison()
                .isEqualTo(responseList);
    }

    @Test
    @DisplayName("프로젝트 조회 시 존재하지 않는 프로젝트 예외 처리")
    void ProjectNotExistError() {
        // given
        Long projectId = 1L;

        // when
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // then
        assertThrows(EmptyResultException.class, () -> {
            projectService.findProjectById(projectId);
        });
    }

    @Test
    @DisplayName("프로젝트 삭제 시 존재하지 않는 프로젝트 예외 처리")
    void EmptyResultDataAccessException() throws Exception {
        // given
        doThrow(EmptyResultDataAccessException.class).when(projectRepository).deleteById(anyLong());

        // then
        assertThrows(EmptyResultException.class, () -> {
            projectService.removeProject(1L);
        }, ErrorCode.PROJECT_DELETED_OR_NOT_EXIST.getMessage());
    }
}