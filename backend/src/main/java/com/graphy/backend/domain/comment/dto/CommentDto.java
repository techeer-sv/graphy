package com.graphy.backend.domain.comment.dto;

import com.graphy.backend.domain.comment.domain.Comment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class GetCommentsResponse {
        private Long projectId;
        private Long commentId;
        private String content;
        private List<ChildComments> childComments;

        public static GetCommentsResponse from(Comment comment) {
            List<ChildComments> childCommentsList = comment.getChildList()
                    .stream()
                    .map(ChildComments::from).collect(Collectors.toList());

            return new GetCommentsResponse(
                    comment.getProject().getId(),
                    comment.getId(),
                    comment.getContent(),
                    childCommentsList
            );
        }
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChildComments {
        private Long parentId;
        private Long commentId;
        private String content;

        public static ChildComments from(Comment childComment) {
            return new ChildComments(childComment.getParent().getId(),
                    childComment.getId(), childComment.getContent());
        }
    }
}
