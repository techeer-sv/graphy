package com.graphy.backend.domain.comment.repository;



import java.util.List;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;

public interface CommentCustomRepository {
    List<GetCommentWithMaskingResponse> findCommentsWithMasking(Long id);
    List<GetReplyListResponse> findReplyList(Long parentId);
}
