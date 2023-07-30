package com.graphy.backend.domain.project.mapper;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.dto.MemberDto;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.ProjectTags;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


import java.util.List;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;
import static com.graphy.backend.domain.project.dto.ProjectDto.*;

@Component
public class ProjectMapper {

    public UpdateProjectResponse toUpdateProjectDto(Project project) {
        return UpdateProjectResponse.builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .content(project.getContent())
                .thumbNail(project.getThumbNail())
                .description(project.getDescription()).build();
    }

    public CreateProjectResponse toCreateProjectDto(Long id) {
        return CreateProjectResponse.builder().projectId(id).build();
    }

    public Project toEntity(CreateProjectRequest dto, Member member) {
        return Project.builder()
                .member(member)
                .projectName(dto.getProjectName())
                .content(dto.getContent())
                .description(dto.getDescription())
                .thumbNail(dto.getThumbNail())
                .projectTags(new ProjectTags())
                .build();
    }

    public GetProjectResponse toGetProjectDto(Project project) {
        return GetProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .techTags(project.getTagNames())
                .thumbNail(project.getThumbNail())
                .member(MemberDto.GetMemberResponse.toDto(project.getMember()))
                .build();
    }

    public GetProjectDetailResponse toGetProjectDetailDto(Project project,
                                                          List<GetCommentWithMaskingResponse> comments) {

        return GetProjectDetailResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .techTags(project.getTagNames())
                .content(project.getContent())
                .commentsList(comments)
                .member(MemberDto.GetMemberResponse.toDto(project.getMember()))
                .thumbNail(project.getThumbNail())
                .build();
    }

    public GetProjectInfoResponse toProjectInfoDto(Project project) {
        return GetProjectInfoResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .build();
    }

    public Page<GetProjectResponse> toDtoList(Page<Project> projects) {
        return projects.map(this::toGetProjectDto);
    }

}
