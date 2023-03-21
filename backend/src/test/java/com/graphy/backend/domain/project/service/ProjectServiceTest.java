package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;


@SpringBootTest
class ProjectServiceTest {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private ProjectService projectService;
    @Autowired private EntityManager entityManager;

    @AfterEach
    public void clean(){projectRepository.deleteAll();}

    @Test
    public void 프로젝트삭제() throws Exception {
        //given
        Project project = Project.builder()
                .projectName("name")
                .content("content")
                .description("des").build();

        projectRepository.save(project);

        //when
        projectRepository.deleteById(project.getId());

        //then
        Assertions.assertTrue(project.isDeleted());
    }
}