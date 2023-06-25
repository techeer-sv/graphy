package com.graphy.backend.domain.comment.dto;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class CreateCommentRequest {

        @NotBlank
        private String content;

        private Long projectId;

        private Long parentId;

        public static Comment to(CreateCommentRequest createCommentRequest, Project project, Comment parentComment, Member member) {
            return Comment.builder()
                    .member(member)
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
}