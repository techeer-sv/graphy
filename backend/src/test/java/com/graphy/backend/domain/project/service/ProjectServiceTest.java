package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.ProjectTags;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.dto.request.CreateProjectRequest;
import com.graphy.backend.domain.project.dto.request.GetProjectsRequest;
import com.graphy.backend.domain.project.dto.request.UpdateProjectRequest;
import com.graphy.backend.domain.project.dto.response.CreateProjectResponse;
import com.graphy.backend.domain.project.dto.response.GetProjectResponse;
import com.graphy.backend.domain.project.dto.response.UpdateProjectResponse;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.auth.service.CustomUserDetailsService;
import com.graphy.backend.global.common.PageRequest;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest extends MockTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectTagService projectTagService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("프로젝트 수정 테스트")
    public void updateProject() throws Exception {
        //given
        Project project = Project.builder()
                .id(1L)
                .projectTags(new ProjectTags())
                .projectName("beforeUpdate")
                .description("des")
                .thumbNail("thumb")
                .content("content")
                .build();

        UpdateProjectRequest request = UpdateProjectRequest.builder()
                .projectName("afterUpdate")
                .description("des")
                .thumbNail("thumb")
                .content("content")
                .techTags(new ArrayList<>(Arrays.asList("Spring", "Django")))
                .build();

        UpdateProjectResponse response = UpdateProjectResponse.builder()
                .projectName(request.getProjectName())
                .description(request.getDescription())
                .thumbNail(request.getThumbNail())
                .content(request.getContent())
                .techTags(request.getTechTags())
                .build();

        Tag tag1 = Tag.builder().tech("Spring").build();
        Tag tag2 = Tag.builder().tech("Django").build();
        Tags tags = new Tags(Arrays.asList(tag1, tag2));

        project.updateProject(request.getProjectName(), request.getContent(), request.getDescription(),
                tags, request.getThumbNail());

        //when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(UpdateProjectResponse.from(project)).thenReturn(response);

        UpdateProjectResponse result = projectService.modifyProject(project.getId(), request);

        assertAll(
                () -> assertThat(result.getProjectName()).isEqualTo(project.getProjectName()),
                () -> assertThat(result.getDescription()).isEqualTo(project.getDescription()),
                () -> assertThat(result.getThumbNail()).isEqualTo(project.getThumbNail()),
                () -> assertThat(result.getTechTags()).isEqualTo(new ArrayList<>(Arrays.asList("Spring", "Django")))
        );
    }

    @Test
    @DisplayName("프로젝트 생성 테스트")
    public void createProject() throws Exception {
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

        CreateProjectResponse response = CreateProjectResponse.builder()
                .projectId(project.getId())
                .build();

        Tag tag1 = Tag.builder().tech("Spring").build();
        Tag tag2 = Tag.builder().tech("Django").build();

        //when
        when(request.toEntity(member)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(CreateProjectResponse.from(project.getId())).thenReturn(response);
        CreateProjectResponse result = projectService.addProject(request, member);

        //then
        assertThat(result.getProjectId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("프로젝트 리스트 조회")
    public void getProjects() throws Exception {
        //given
        GetProjectsRequest request = GetProjectsRequest.builder().projectName("name").build();

        Project project1 = Project.builder().projectName("test1").build();
        Project project2 = Project.builder().projectName("test2").build();

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

        when(GetProjectResponse.listOf(projects)).thenReturn(responses);

        List<GetProjectResponse> result = projectService.findProjectList(request, pageable);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getProjectName()).isEqualTo("test1");
        assertThat(result.get(1).getProjectName()).isEqualTo("test2");
        assertThat(result).isEqualTo(responseList);
    }

    @Test
    @DisplayName("프로젝트 삭제")
    public void deleteProject() throws Exception {
        //when
        projectService.removeProject(1L);

        //then
        verify(projectRepository).deleteById(1L);
    }

    @Test
    @DisplayName("프로젝트 조회 시 존재하지 않는 프로젝트 예외 처리")
    public void ProjectNotExistError() {
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
    public void EmptyResultDataAccessException() throws Exception {
        // given
        doThrow(EmptyResultDataAccessException.class).when(projectRepository).deleteById(anyLong());

        // then
        assertThrows(EmptyResultException.class, () -> {
            projectService.removeProject(1L);
        }, ErrorCode.PROJECT_DELETED_OR_NOT_EXIST.getMessage());
    }
}