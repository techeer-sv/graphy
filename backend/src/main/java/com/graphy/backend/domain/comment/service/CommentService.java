package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.GetReplyListDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final ProjectRepository projectRepository;

    public CreateCommentResponse createComment(CreateCommentRequest dto) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST));

        Comment parentComment = null;
        if (dto.getParentId() != null) {
            parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new EmptyResultException(ErrorCode.COMMENT_DELETED_OR_NOT_EXIST));
        }

        Comment entity = CreateCommentRequest.to(dto, project, parentComment);

        return new CreateCommentResponse(commentRepository.save(entity).getId());
    }

    @Transactional
    public Long updateComment(Long commentId, UpdateCommentRequest dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EmptyResultException(ErrorCode.COMMENT_DELETED_OR_NOT_EXIST));

        comment.updateContent(dto.getContent());
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EmptyResultException(ErrorCode.PROJECT_DELETED_OR_NOT_EXIST));

        comment.delete();
    }

    public List<GetReplyListDto> getReplyList(Long commentId) {
        return commentRepository.findReplyList(commentId);
    }
}
