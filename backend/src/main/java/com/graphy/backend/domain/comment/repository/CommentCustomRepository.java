package com.graphy.backend.domain.comment.repository;


import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.response.GetCommentWithMaskingResponse;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;

import java.util.List;

public interface CommentCustomRepository {
    List<GetCommentWithMaskingResponse> findCommentsWithMasking(Long id);
    List<Comment> findReplyList(Long parentId);
}
