package com.graphy.backend.domain.comment.dto;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
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
    @NoArgsConstructor
    public static class CreateCommentResponse {
        private Long commentId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetCommentWithMaskingResponse {
        private String content;
        private Long commentId;
        private LocalDateTime createdAt;
        private String nickname;
        private Long childCount;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetReplyListResponse {
        private String nickname;
        private String content;
        private Long commentId;
        private LocalDateTime createdAt;


        public GetReplyListResponse(String content, Long commentId, LocalDateTime createdAt, String nickname) {
            this.nickname = nickname;
            this.content = content;
            this.commentId = commentId;
            this.createdAt = createdAt;
        }
    }
}