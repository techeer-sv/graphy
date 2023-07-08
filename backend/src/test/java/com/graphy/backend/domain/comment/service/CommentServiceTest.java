package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.dto.ReplyListDto;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentRequest;
import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest extends MockTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Test
    @WithMockUser(username = "graphy@gmail.com", password = "1234", roles = "USER")
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
    @DisplayName("댓글과 답글 상관없이 삭제")
    public void deleteComment() throws Exception {
        //given
        Comment comment = Comment.builder().id(1L).content("댓글").build();
        Comment reComment = Comment.builder().id(2L).content("대댓글").parent(comment).build();

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        given(commentRepository.findById(2L)).willReturn(Optional.of(reComment));

        // when
        commentService.deleteComment(1L);
        commentService.deleteComment(2L);

        //then
        assertTrue(comment.isDeleted());
        assertTrue(reComment.isDeleted());
    }

    @Test
    @DisplayName("댓글 수정")
    public void updateComment() throws Exception {
        //given
        Comment comment = Comment.builder().id(1L).content("수정 전").build();
        CommentDto.UpdateCommentRequest commentRequest = new CommentDto.UpdateCommentRequest("수정 후");

        // when
        comment.updateContent(commentRequest.getContent());
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        //then
        assertTrue(comment.getContent().equals("수정 후"));
        Long result = commentService.updateComment(1L, commentRequest);
        assertTrue(result==1L);
    }

    @Test
    @DisplayName("답글 조회")
    public void getReplyList() throws Exception {

        List<ReplyListDto> list = new ArrayList<>();
        ReplyListDto dto1 = new ReplyListDto() {
            @Override
            public String getContent() {
                return "comment1";
            }

            @Override
            public Long getCommentId() {
                return 1L;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return null;
            }
        };

        ReplyListDto dto2 = new ReplyListDto() {
            @Override
            public String getContent() {
                return "comment2";
            }

            @Override
            public Long getCommentId() {
                return 2L;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return null;
            }
        };

        list.add(dto1);
        list.add(dto2);

        // when
        when(commentRepository.findReplyList(1L)).thenReturn(list);
        List<ReplyListDto> result = commentService.getReplyList(1L);

        //then
        assertTrue(result.get(0).getContent().equals("comment1"));
        assertTrue(result.get(1).getContent().equals("comment2"));

        assertTrue(result.get(0).getCommentId() == 1L);
        assertTrue(result.get(1).getCommentId() == 2L);
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