package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;

import java.util.List;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;


public interface CommentCustomRepository {
    List<Comment> findCommentsWithMasking(Long projectId);
}
