package com.graphy.backend.domain.project.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTagsTest {
    
    @Test
    @DisplayName("프로젝트 태그 생성자 테스터")
    public void projectTagsInit() throws Exception {
        //when
        ProjectTags projectTags = new ProjectTags();

        //then
        Assertions.assertAll(
                () -> assertNotNull(projectTags.getValue()),
                () -> assertTrue(projectTags.getValue().isEmpty())
        );
    }

    @Test
    @DisplayName("태그 삭제 테스트")
    void removeTagsTest() {
        Tag tag1 = Tag.builder().tech("Spring").build();
        Tag tag2 = Tag.builder().tech("Spring Boot").build();

        ProjectTags projectTags = new ProjectTags();

        Project project = new Project();

        Tags tags = new Tags(Arrays.asList(tag1, tag2));

        projectTags.add(project, tags);

        assertFalse(projectTags.getValue().isEmpty());

        projectTags.clear();

        assertTrue(projectTags.getValue().isEmpty());
    }

    @Test
    @DisplayName("태그 조회 테스트")
    void getTagNamesTest() {
        ProjectTags projectTags = new ProjectTags();
        Project project = new Project();

        Tag tag1 = Tag.builder().tech("Spring").build();
        Tag tag2 = Tag.builder().tech("Spring Boot").build();

        Tags tags = new Tags(Arrays.asList(tag1, tag2));

        projectTags.add(project, tags);

        List<String> tagNames = projectTags.getTagNames();

        assertTrue(tagNames.containsAll(Arrays.asList("Spring", "Spring Boot")));
    }

    @Test
    @DisplayName("태그 추가 테스트")
    void addTagTest() {
        ProjectTags projectTags = new ProjectTags();
        Project project = new Project();

        Tag tag1 = Tag.builder().tech("Spring").build();
        Tag tag2 = Tag.builder().tech("Spring Boot").build();

        Tags tags = new Tags(Arrays.asList(tag1, tag2));

        projectTags.add(project, tags);

        assertEquals(2, projectTags.getValue().size());
        assertTrue(projectTags.getTagNames().containsAll(Arrays.asList("Spring", "Spring Boot")));  // 확인: 추가된 태그들이 정상적으로 존재
    }
}
