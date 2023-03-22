package com.graphy.backend.domain.project.controller;

import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectControllerTest {
    @Autowired private ProjectRepository projectRepository;
    @Autowired private ProjectService projectService;

    @Test
    @DisplayName("프로젝트 soft delete 테스트")
    public void 삭제된_프로젝트는_조회X() throws Exception {
        //given
        Project project1 = Project.builder().projectName("projectTest1").content("content1").build();
        Project project2 = Project.builder().projectName("projectTest2").content("content2").build();
        Project project3 = Project.builder().projectName("projectTest3").content("content3").build();

        Project savedProject1 = projectRepository.save(project1);
        Project savedProject2 = projectRepository.save(project2);
        Project savedProject3 = projectRepository.save(project3);

        //when
        projectService.deleteProject(savedProject1.getId()); // savedProject1의 is_deleted를 true로 변경

        //then
        Optional<Project> result1 = projectRepository.findById(savedProject1.getId());
        Optional<Project> result2 = projectRepository.findById(savedProject2.getId());
        Optional<Project> result3 = projectRepository.findById(savedProject3.getId());

        assertThat(result1).isEmpty(); // is_deleted == true인 데이터는 조회가 안 되므로 True
        assertThat(result2).isNotEmpty(); // is_deleted == false인 데이터는 조회되므로 에러가 발생
        assertThat(result3).isNotEmpty(); // 위와 같은 이유로 True
    }
}