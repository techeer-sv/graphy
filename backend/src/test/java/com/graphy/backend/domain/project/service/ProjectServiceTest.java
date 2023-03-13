package com.graphy.backend.domain.project.service;

import com.graphy.backend.domain.project.entity.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;


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