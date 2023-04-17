package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentRequest;
import static com.graphy.backend.domain.comment.dto.CommentDto.CreateCommentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Test
    void 댓글이_생성된다() {

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
    void 대댓글이_생성된다() {

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
    public void 대댓글의_경우_바로_삭제() throws Exception {
        //given
        Comment comment = Comment.builder().id(1L).content("댓글").build();
        Comment reComment = Comment.builder().id(2L).content("대댓글1").parent(comment).build();

        // mocking
        given(commentRepository.findById(2L)).willReturn(Optional.of(reComment));

        // when
        commentService.deleteComment(2L);

        //then
        assertTrue(reComment.isDeleted());
    }

    @Test
    public void 대댓글_삭제_후에_부모댓글이_삭제된_상태고_대댓글이_없으면_부모댓글도_삭제() throws Exception {

        //given
        Comment comment = Comment.builder().id(1L).content("삭제된 댓글입니다.").childList(new ArrayList<>()).build();
        Comment reComment = Comment.builder().id(2L).content("대댓글").parent(comment).build();
        comment.getChildList().add(reComment);

        //mocking
        given(commentRepository.findById(2L)).willReturn(Optional.of(reComment));

        // when
        commentService.deleteComment(2L);

        //then
        assertTrue(comment.isDeleted());
        assertTrue(reComment.isDeleted());
    }

    @Test
    public void 댓글의_경우_대댓글이_남았으면_삭제된_댓글입니다_표시() throws Exception {

        //given
        Comment comment = Comment.builder().id(2L).content("댓글").childList(new ArrayList<>()).build();

        Comment reComment1 = Comment.builder().content("대댓글1").build();
        Comment reComment2 = Comment.builder().content("대댓글2").build();

        comment.getChildList().add(reComment1);
        comment.getChildList().add(reComment2);

        // mocking
        given(commentRepository.findById(2L)).willReturn(Optional.of(comment));

        //when
        System.out.println("============삭제 전============");
        System.out.println(comment.getContent()+"\n");
        commentService.deleteComment(2L);

        //then
        System.out.println("============삭제 후============");
        System.out.println(comment.getContent());
        assertEquals(comment.getContent(), "삭제된 댓글입니다.");
    }

    @Test
    public void 댓글의_경우_대댓글이_없으면_삭제() throws Exception {

        //given

        Comment comment = Comment.builder().id(2L).content("댓글").childList(new ArrayList<>()).build();

        //mocking
        given(commentRepository.findById(2L)).willReturn(Optional.ofNullable(comment));

        // when
        commentService.deleteComment(2L);

        // then
        assertTrue(comment.isDeleted());
    }
}