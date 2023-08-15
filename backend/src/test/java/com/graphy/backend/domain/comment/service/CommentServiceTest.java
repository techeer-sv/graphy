package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.auth.jwt.CustomUserDetailsService;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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
    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("댓글이 생성된다")
    void createCommentTest() {

        //given

        // mocking

        //when

        //then
    }

    @Test
    @DisplayName("대댓글이 생성된다")
    void createCommentTest2() {

        //given
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
    }

    @Test
    @DisplayName("답글 조회")
    public void getReplyList() throws Exception {

    }

    @Test
    @DisplayName("삭제되거나 없는 댓글 수정시 오류가 발생한다")
    void updateCommentTest() {

        //given
    }
}