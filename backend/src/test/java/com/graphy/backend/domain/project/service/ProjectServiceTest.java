package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.domain.*;
import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.repository.ProjectTagRepository;
import com.graphy.backend.domain.project.repository.TagRepository;
import com.graphy.backend.global.common.PageRequest;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.test.MockTest;
import org.assertj.core.api.Assertions;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest extends MockTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectTagRepository projectTagRepository;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProjectMapper mapper;

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
        when(tagRepository.findTagByTech(anyString())).thenReturn(tag1, tag2);
        when(mapper.toUpdateProjectDto(project)).thenReturn(response);

        UpdateProjectResponse result = projectService.updateProject(project.getId(), request);

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
        when(mapper.toEntity(any(CreateProjectRequest.class))).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(tagRepository.findTagByTech(anyString())).thenReturn(tag1, tag2);
        when(mapper.toCreateProjectDto(project.getId())).thenReturn(response);

        CreateProjectResponse result = projectService.createProject(request);

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

        when(mapper.toDtoList(projects)).thenReturn(responses);

        List<GetProjectResponse> result = projectService.getProjects(request, pageable);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getProjectName()).isEqualTo("test1");
        assertThat(result.get(1).getProjectName()).isEqualTo("test2");
        assertThat(result).isEqualTo(responseList);
    }
    
    @Test
    @DisplayName("프로젝트 상세 조회")
    public void getProjectById() throws Exception {
        //given
        Project project = Project.builder()
                .id(1L)
                .projectName("project").build();

        List<CommentWithMaskingDto> comments = new ArrayList<>();
        GetProjectDetailResponse response = GetProjectDetailResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .build();


        //when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(commentRepository.findCommentsWithMasking(project.getId())).thenReturn(comments);
        when(mapper.toGetProjectDetailDto(project, comments)).thenReturn(response);

        GetProjectDetailResponse result = projectService.getProjectById(1L);

        //then
        assertThat(response.getId()).isEqualTo(result.getId());
        assertThat(response.getProjectName()).isEqualTo(result.getProjectName());
    }

    @Test
    @DisplayName("프로젝트 삭제")
    public void deleteProject() throws Exception {
        //when
        projectService.deleteProject(1L);

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
            projectService.getProjectById(projectId);
        });
    }

    @Test
    @DisplayName("프로젝트 삭제 시 존재하지 않는 프로젝트 예외 처리")
    public void EmptyResultDataAccessException() throws Exception {
        // given
        doThrow(EmptyResultDataAccessException.class).when(projectRepository).deleteById(anyLong());

        // then
        assertThrows(EmptyResultException.class, () -> {
            projectService.deleteProject(1L);
        }, ErrorCode.PROJECT_DELETED_OR_NOT_EXIST.getMessage());
    }
}