package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.project.mapper.ProjectMapper;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.test.MockTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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