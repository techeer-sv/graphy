package com.graphy.backend.domain.comment.dto.request;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long projectId;

    private Long parentId;

    public Comment toEntity(Project project,
                            Comment parentComment,
                            Member member) {
        return Comment.builder()
                .member(member)
                .content(content)
                .parent(parentComment)
                .project(project)
                .build();
    }
}