package com.graphy.backend.domain.project.util;

import com.graphy.backend.domain.comment.repository.CommentRepository;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.domain.ProjectTags;
import com.graphy.backend.domain.project.domain.Tag;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.domain.project.repository.TagRepository;
import com.graphy.backend.domain.project.service.ProjectService;
import com.graphy.backend.domain.project.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectInitializer implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    private final CommentRepository commentRepository;

    private final TagRepository tagRepository;
    private final TagService tagService;

    private final RedisTemplate<String, Long> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (tagRepository.existsById(1L)) return;

        ClassPathResource resource = new ClassPathResource("tag.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        String s;
        List<String> tagList = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            Tag tag = Tag.builder().tech(s).build();
            tagList.add(tag.getTech());
            tagRepository.save(tag);
        }
        br.close();

        String[] team = new String[]{"Yujin-Baek", "baekhangyeol", "kimhalin", "youKeon", "Mayreeel"};
        memberRepository.save(Member.builder()
                .email("admin@graphy.com")
                .nickname("ADMIN")
                .password(encoder.encode("12345"))
                .build());

        for (int i = 0; i < 5; i++) {
            memberRepository.save(Member.builder()
                    .email(team[i] + "@graphy.com")
                    .nickname(team[i])
                    .password(encoder.encode("12345"))
                    .build());
        }

        int randomNumber = (int) (Math.random() * 4) + 3;
        List<Member> members = memberRepository.findAll();
        for (int i = 1; i <= 100; i++) {
            Tags tags = null;
            for (int j = 0; j < randomNumber; j++) {
                Collections.shuffle(tagList);
                tags = tagService.findTagListByName(tagList.subList(0, 3 + (int) (Math.random() * 4)));
            }
            
            Project project = Project.builder()
                    .member(members.get((int) (Math.random() * 5) + 1))
                    .projectName("Project" + i)
                    .description("Description" + i)
                    .viewCount(30 + (int) (Math.random() * (900 - 30 + 1)))
                    .content("Content" + i)
                    .thumbNail("ThumbNail" + i)
                    .projectTags(new ProjectTags())
                    .build();
            project.addTag(tags);
            projectRepository.save(project);
//            for (int j = 0; j < 10; j++) {
//                for (int k = 0; k < (Math.random() * 5) + 1; k++) {
//
//                }
//                commentRepository.save(Comment.builder()
//                        .parent(null)
//                        .project(project)
//                        .member(members.get((int) (Math.random() * 5) + 1))
//                                .childList()
//                        .content("comment" + j)
//                        .build());
//            }
        }

        // Redis Sorted Set에 데이터 저장
        List<Project> projects = projectRepository.findAll();
        for (Project p : projects) {
            redisTemplate.opsForZSet().incrementScore("ranking", p.getId(), p.getViewCount());
        }

        // Redis에 상위 10개 프로젝트 저장
        projectService.initializeProjectRanking();
    }
}