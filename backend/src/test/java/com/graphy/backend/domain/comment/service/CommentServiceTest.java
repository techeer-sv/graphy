package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.request.CreateCommentRequest;
import com.graphy.backend.domain.comment.dto.request.UpdateCommentRequest;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.error.exception.EmptyResultException;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    private Member member;
    private Project project;
    private Comment parentComment;
    @BeforeEach
    public void setup() {
        member = Member.builder()
                .id(1L)
                .email("graphy@gmail.com")
                .nickname("name")
                .role(Role.ROLE_USER)
                .build();

        project = Project.builder()
                .id(1L)
                .member(member)
                .build();

        parentComment = Comment.builder()
                .id(1L)
                .content("parentComment")
                .member(member)
                .project(project)
                .build();

    }

    @Test
    @DisplayName("댓글이 생성된다")
    void addCommentTest() {
        //given
        CreateCommentRequest request = new CreateCommentRequest("content", project.getId(), null);

        //when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.ofNullable(project));

        //then
        assertDoesNotThrow(() -> commentService.addComment(request, member));
    }

    @Test
    @DisplayName("댓글 생성 시 프로젝트가 존재하지 않으면 예외가 발생한다")
    void addCommentNotExistProjectExceptionTest() {
        // given
        Long 존재하지_않는_프로젝트_ID = 0L;
        CreateCommentRequest request = new CreateCommentRequest("content", 존재하지_않는_프로젝트_ID, null);

        // when, then
        assertThatThrownBy(
                () -> commentService.addComment(request, member))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("이미 삭제되거나 존재하지 않는 프로젝트");
    }

    @Test
    @DisplayName("답글이 생성된다")
    void addReCommentTest() {
        //given
        CreateCommentRequest request = new CreateCommentRequest("content", project.getId(), parentComment.getId());

        //when
        when(projectRepository.findById(request.getProjectId())).thenReturn(Optional.ofNullable(project));
        when(commentRepository.findById(request.getParentId())).thenReturn(Optional.ofNullable(parentComment));

        //then
        assertDoesNotThrow(() -> commentService.addComment(request, member));
    }

    @Test
    @DisplayName("답글 생성 시 댓글이 존재하지 않으면 예외가 발생한다")
    void addReCommentNotExistParentCommentExceptionTest() {
        // given
        Long 존재하지_않는_댓글_ID = 0L;
        CreateCommentRequest request = new CreateCommentRequest("content", project.getId(), 존재하지_않는_댓글_ID);

        // when
        when(projectRepository.findById(request.getProjectId())).thenReturn(Optional.ofNullable(project));

        // then
        assertThatThrownBy(
                () -> commentService.addComment(request, member))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("이미 삭제되거나 존재하지 않는 댓글");
    }

    @Test
    @DisplayName("댓글을 수정한다")
    void modifyCommentTest() {
        //given
        UpdateCommentRequest request = new UpdateCommentRequest("updateContent");

        //when
        when(commentRepository.findById(parentComment.getId())).thenReturn(Optional.ofNullable(parentComment));
        commentService.modifyComment(parentComment.getId(), request);

        //then
        assertThat(parentComment.getContent()).isEqualTo(request.getContent());
    }

    @Test
    @DisplayName("답글 수정 시 댓글이 존재하지 않으면 예외가 발생한다")
    void modifyReCommentNotExistParentCommentExceptionTest() {
        // given
        Long 존재하지_않는_댓글_ID = 0L;
        UpdateCommentRequest request = new UpdateCommentRequest("updateContent");

        // when, then
        assertThatThrownBy(
                () -> commentService.modifyComment(존재하지_않는_댓글_ID, request))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("이미 삭제되거나 존재하지 않는 댓글");
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    void removeCommentTest() {
        //when
        when(commentRepository.findById(parentComment.getId())).thenReturn(Optional.ofNullable(parentComment));
        commentService.deleteComment(parentComment.getId());

        //then
        assertThat(parentComment.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("답글 삭제 시 댓글이 존재하지 않으면 예외가 발생한다")
    void removeReCommentNotExistParentCommentExceptionTest() {
        // given
        Long 존재하지_않는_댓글_ID = 0L;

        // when, then
        assertThatThrownBy(
                () -> commentService.deleteComment(존재하지_않는_댓글_ID))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("이미 삭제되거나 존재하지 않는 댓글");
    }

    @Test
    @DisplayName("답글 목록을 조회한다")
    void findReCommentListTest() {
        //given
        List<GetReplyListResponse> reCommentList = new LinkedList<>(Arrays.asList(
                GetReplyListResponse.builder().nickname(member.getNickname()).content("reComment1").build(),
                GetReplyListResponse.builder().nickname(member.getNickname()).content("reComment2").build(),
                GetReplyListResponse.builder().nickname(member.getNickname()).content("reComment3").build()
        ));

        //when
        when(commentRepository.findReplyList(parentComment.getId())).thenReturn(reCommentList);
        List<GetReplyListResponse> actual = commentService.findCommentList(parentComment.getId());

        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(reCommentList);
    }

    @Test
    @DisplayName("답글 목록 조회 시 목록이 없으면 예외가 발생한다")
    void findReCommentListEmptyListExceptionTest() {
        // given
        Long 답글이_존재하지_않는_댓글_ID = 0L;

        //when
        when(commentRepository.findReplyList(답글이_존재하지_않는_댓글_ID)).thenReturn(Collections.emptyList());

        // then
        assertThatThrownBy(
                () -> commentService.findCommentList(답글이_존재하지_않는_댓글_ID))
                .isInstanceOf(EmptyResultException.class)
                .hasMessageContaining("이미 삭제되거나 존재하지 않는 댓글");
    }
}