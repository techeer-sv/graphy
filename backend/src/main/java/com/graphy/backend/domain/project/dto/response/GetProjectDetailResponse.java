package com.graphy.backend.domain.project.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.graphy.backend.domain.comment.dto.response.GetCommentWithMaskingResponse;
import com.graphy.backend.domain.member.dto.response.GetMemberResponse;
import com.graphy.backend.domain.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.graphy.backend.domain.member.dto.response.GetMemberResponse.from;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectDetailResponse implements Serializable {

    private Long id;

    private String projectName;

    private String content;

    private String description;

    private String thumbNail;

    private List<GetCommentWithMaskingResponse> commentsList;
    /**
     * TODO
     * viewCount 추가
     */

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private List<String> techTags;

    private GetMemberResponse member;

    public static GetProjectDetailResponse of(Project project,
                                         List<GetCommentWithMaskingResponse> comments) {

        return GetProjectDetailResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .techTags(project.getTagNames())
                .content(project.getContent())
                .commentsList(comments)
                .member(from(project.getMember()))
                .thumbNail(project.getThumbNail())
                .build();
    }
}