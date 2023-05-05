package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

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
