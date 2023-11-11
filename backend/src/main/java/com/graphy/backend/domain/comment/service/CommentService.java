package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.request.CreateCommentRequest;
import com.graphy.backend.domain.comment.dto.request.UpdateCommentRequest;
import com.graphy.backend.domain.comment.dto.response.GetCommentWithMaskingResponse;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;

    public void addComment(CreateCommentRequest dto, Member loginUser) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST));

        Comment parentComment = null;
        if (dto.getParentId() != null) {
            parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new EmptyResultException(ErrorCode.COMMENT_DELETED_OR_NOT_EXIST));
        }

        Comment comment = dto.toEntity(project, parentComment, loginUser);
        commentRepository.save(comment);
    }

    public void modifyComment(Long commentId, UpdateCommentRequest dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EmptyResultException(ErrorCode.COMMENT_DELETED_OR_NOT_EXIST));

        comment.updateContent(dto.getContent());
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EmptyResultException(ErrorCode.COMMENT_DELETED_OR_NOT_EXIST));

        comment.delete();
    }

    public List<GetReplyListResponse> findCommentList(Long commentId) {
        List<GetReplyListResponse> commentList = commentRepository.findReplyList(commentId);

        if (commentList.isEmpty()) throw new EmptyResultException(ErrorCode.COMMENT_DELETED_OR_NOT_EXIST);

        return commentList;

    }

    public List<GetCommentWithMaskingResponse> findCommentListWithMasking(Long projectId) {
        return commentRepository.findCommentsWithMasking(projectId);
    }
}
