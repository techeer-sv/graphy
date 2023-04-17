package com.graphy.backend.domain.comment.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.error.ErrorCode;
import com.graphy.backend.global.error.exception.EmptyResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;
import javax.transaction.Transactional;

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

        // 대댓글인 경우
        if (comment.getParent() != null) {
            // 삭제
            comment.setDeletedTrue();
            //commentRepository.delete(comment);

            //부모 댓글이 삭제된 상태고 다른 대댓글이 없으면 부모 댓글도 삭제
            if (comment.getParent().getContent().equals("삭제된 댓글입니다.") &&
                    comment.getParent().getChildList().size() == 1) {
                comment.getParent().setDeletedTrue();
//                commentRepository.delete(comment.getParent());
            }

            // 부모 댓글인 경우
        } else {

            // 자식 댓글이 없으면 삭제
            if (comment.getChildList().isEmpty()) {
                comment.setDeletedTrue();
//                commentRepository.delete(comment);
            }
            // 자식 댓글이 남았으면 "삭제된 댓글입니다."
            comment.deleteComment(comment);
        }
    }
}
