package com.graphy.backend.domain.project.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProjectTest {
    
    @Test
    @DisplayName("프로젝트 태그 이름 조회")
    public void getTagNameTest() throws Exception {
        //given
        Tag tag = Tag.builder().tech("Spring").build();
        ProjectTag projectTag = ProjectTag.builder().tag(tag).build();
        ProjectTags projectTags = new ProjectTags(Arrays.asList(projectTag));

        Project project = Project.builder().projectTags(projectTags).build();


        //when
        List<String> result = project.getTagNames();

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(1),
                () -> assertThat(result.get(0)).isEqualTo("Spring")
        );
    }

    @Test
    @DisplayName("프로젝트 태그 id 조회")
    public void getTagIdTest() throws Exception {
        //given
        Tag tag = Tag.builder().id(1L).tech("Spring").build();
        ProjectTag projectTag = ProjectTag.builder().tag(tag).build();
        ProjectTags projectTags = new ProjectTags(Arrays.asList(projectTag));

        Project project = Project.builder().projectTags(projectTags).build();


        //when
        List<Long> result = project.getTagIds();

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(1),
                () -> assertThat(result.get(0)).isEqualTo(1L)
        );
    }
}
