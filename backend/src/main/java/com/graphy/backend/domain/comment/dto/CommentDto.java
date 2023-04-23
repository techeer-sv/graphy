package com.graphy.backend.domain.comment.dto;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.project.domain.Project;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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
        private LocalDateTime createdAt;

        public static GetCommentsResponse from(Comment comment) {
            List<ChildComments> childCommentsList = comment.getChildList()
                    .stream()
                    .map(ChildComments::from).collect(Collectors.toList());

            return new GetCommentsResponse(
                    comment.getProject().getId(),
                    comment.getId(),
                    comment.getContent(),
                    childCommentsList,
                    comment.getCreatedAt()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CreateCommentRequest {

        @NotBlank
        private String content;

        private Long projectId;

        private Long parentId;

        public static Comment to(CreateCommentRequest createCommentRequest, Project project, Comment parentComment) {
            return Comment.builder()
                    .content(createCommentRequest.getContent())
                    .parent(parentComment)
                    .project(project)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCommentRequest {

        @NotBlank
        private String content;
    }


    @Getter
    @AllArgsConstructor
    public static class CreateCommentResponse {
        private Long commentId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChildComments {
        private Long parentId;
        private Long commentId;
        private String content;
        private LocalDateTime createdAt;

        public static ChildComments from(Comment childComment) {
            return new ChildComments(
                    childComment.getParent().getId(),
                    childComment.getId(),
                    childComment.getContent(),
                    childComment.getCreatedAt());
        }
    }
}