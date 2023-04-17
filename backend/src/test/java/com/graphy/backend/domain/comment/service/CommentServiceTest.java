package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentRequest;
import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest extends MockTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("댓글이 생성된다")
    void createCommentTest() {

        //given
        CreateCommentRequest dto = new CreateCommentRequest("내용", 1L, null);
        Project project = Project.builder().id(1L).content("테스트 프로젝트").description("테스트 프로젝트 한 줄 소개").projectName("테스트").build();
        Comment comment = Comment.builder().id(1L).content("내용").project(project).parent(null).build();

        // mocking
        given(commentRepository.save(any()))
                .willReturn(comment);
        given(projectRepository.findById(1L))
                .willReturn(Optional.ofNullable(project));
        given(commentRepository.findById(1L))
                .willReturn(Optional.ofNullable(comment));

        //when
        CreateCommentResponse response = commentService.createComment(dto);

        //then
        Comment findComment = commentRepository.findById(response.getCommentId()).get();
        assertEquals(dto.getContent(), findComment.getContent());
    }

    @Test
    @DisplayName("대댓글이 생성된다")
    void createCommentTest2() {

        //given
        CreateCommentRequest dto = new CreateCommentRequest("내용", 1L, 1L);
        Project project = Project.builder().id(1L).content("테스트 프로젝트").description("테스트 프로젝트 한 줄 소개").projectName("테스트").build();

        Comment parentComment = Comment.builder().id(1L).content("내용").project(project).parent(null).build();
        Comment comment = Comment.builder().id(2L).content("내용").project(project).parent(parentComment).build();

        // mocking
        given(commentRepository.save(any()))
                .willReturn(comment);

        given(projectRepository.findById(1L))
                .willReturn(Optional.of(project));
        given(commentRepository.findById(1L))
                .willReturn(Optional.of(parentComment));
        given(commentRepository.findById(2L))
                .willReturn(Optional.of(comment));


        //when
        CreateCommentResponse response = commentService.createComment(dto);

        //then
        Comment findComment = commentRepository.findById(response.getCommentId()).get();
        assertEquals(parentComment, findComment.getParent());
        assertEquals(dto.getContent(), findComment.getContent());
    }

    @Test
    @DisplayName("삭제되거나 없는 댓글 수정시 오류가 발생한다")
    void updateCommentTest() {

        //given
        Long commentId = 1L;

        String updatedContent = "수정된 내용";

        CommentDto.UpdateCommentRequest commentRequest = new CommentDto.UpdateCommentRequest(updatedContent);

        // mocking
        given(commentRepository.findById(1L))
                .willReturn(Optional.empty());
        //when

        //then
        assertThrows(EmptyResultDataAccessException.class, () -> {
            commentService.updateComment(1L, commentRequest);
        });
    }

}