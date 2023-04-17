package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private CommentService commentService;


//    @Test
//    public void 댓글과_대댓글이_조회된다() throws Exception {
//        //given
//        Project project = Project.builder().id(1L).content("project content").description("project dec").projectName("project").build();
//
//        Comment parentComment = Comment.builder().content("1번 댓글").project(project).build();
//        Comment childComment = Comment.builder().content("1번 댓글의 답글").project(project).parent(parentComment).build();
//        CommentDto.CreateCommentRequest dto = new CommentDto.CreateCommentRequest("1번 댓글", 1L, 1L);
//
//
//        given(commentRepository.save(any())).willReturn(parentComment);
//        given(projectRepository.findById(project.getId())).willReturn(Optional.of(project));
//        given(commentRepository.findById(parentComment.getId())).willReturn(Optional.of(parentComment));
//        given(commentRepository.findById(childComment.getId())).willReturn(Optional.of(childComment));
//
//        //when
//        GetProjectDetailResponse response = projectService.getProjectById(project.getId());
//        commentService.createComment(dto);
//
//        //then
//        assertTrue(response.getCommentsList().contains());
//    }

}
