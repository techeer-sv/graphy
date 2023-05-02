package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentDto;
import com.graphy.backend.domain.comment.dto.GetCommentWithMaskingDto;
import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.ProjectTag;
import com.graphy.backend.domain.project.domain.ProjectTags;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.dto.ProjectDto;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.graphy.backend.domain.project.dto.ProjectDto.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest extends MockTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ProjectMapper mapper;

    @InjectMocks
    private ProjectService projectService;


}
