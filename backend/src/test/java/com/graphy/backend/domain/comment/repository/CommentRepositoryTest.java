package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.response.GetCommentWithMaskingResponse;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.domain.member.repository.MemberRepository;
import com.graphy.backend.domain.project.domain.Project;
import com.graphy.backend.domain.project.repository.ProjectRepository;
import com.graphy.backend.global.config.JpaAuditingConfig;
import com.graphy.backend.global.config.QueryDslConfig;
import com.graphy.backend.test.config.TestProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({
        JpaAuditingConfig.class,
        QueryDslConfig.class
})
@ActiveProfiles(TestProfile.TEST)
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private Member member;
    private Project project;
    private Comment parentComment;
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    @BeforeEach
    public void setup() {
        member = memberRepository.save(
                Member.builder()
                .email("graphy@gmail.com")
                .role(Role.ROLE_USER)
                .followerCount(0)
                .followingCount(0)
                .introduction("introduce")
                .password("password")
                .nickname("name")
                .role(Role.ROLE_USER)
                .build()
        );

        project = projectRepository.save(
                Project.builder()
                .projectName("name")
                .member(member)
                .build()
        );

        parentComment = commentRepository.save(Comment.builder()
                .content("parentComment")
                .member(member)
                .project(project)
                .build()
        );

        comment1 = commentRepository.save(Comment.builder()
                .content("comment1-1")
                .member(member)
                .parent(parentComment)
                .project(project)
                .build()
        );

        comment2 = commentRepository.save(Comment.builder()
                .content("comment1-2")
                .member(member)
                .parent(parentComment)
                .project(project)
                .build()
        );

        comment3 = commentRepository.save(Comment.builder()
                .content("comment1-3")
                .member(member)
                .parent(parentComment)
                .project(project)
                .build()
        );
    }

    @Test
    @DisplayName("프로젝트 ID를 입력 받으면 마스킹을 적용한 댓글 목록을 조회한다")
    public void findCommentWithMakingTest() throws Exception {
        // given
        Comment parentComment2 = commentRepository.save(Comment.builder()
                .content("comment1")
                .member(member)
                .project(project)
                .build()
        );
        parentComment2.delete();


        // when
        List<GetCommentWithMaskingResponse> actual = commentRepository.findCommentsWithMasking(project.getId());

        // then
        Assertions.assertThat(actual.size()).isEqualTo(2);


        Assertions.assertThat(actual.get(0).getCommentId()).isEqualTo(parentComment.getId());
        Assertions.assertThat(actual.get(0).getContent()).isEqualTo(parentComment.getContent());
        Assertions.assertThat(actual.get(0).getNickname()).isEqualTo(parentComment.getMember().getNickname());
        Assertions.assertThat(actual.get(0).getChildCount()).isEqualTo(3);

        Assertions.assertThat(actual.get(1).getContent()).isEqualTo("삭제된 댓글입니다.");
    }

    @Test
    @DisplayName("댓글 목록 조회 시 댓글이 없으면 빈 리스트가 반환된다")
    public void findCommentWithMakingEmptyListTest() throws Exception {
        // given
        clearRepository();

        // when
        List<GetCommentWithMaskingResponse> actual = commentRepository.findCommentsWithMasking(project.getId());

        // then
        assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글 ID를 입력 받아 답글 목록을 조회한다")
    public void findReCommentListTest() throws Exception {
        // when
        List<GetReplyListResponse> actual = commentRepository.findReplyList(parentComment.getId());

        // then
        assertThat(actual.size()).isEqualTo(3);

        assertThat(actual.get(0).getNickname()).isEqualTo(member.getNickname());
        assertThat(actual.get(0).getContent()).isEqualTo(comment1.getContent());
        assertThat(actual.get(0).getCommentId()).isEqualTo(comment1.getId());

        assertThat(actual.get(1).getNickname()).isEqualTo(member.getNickname());
        assertThat(actual.get(1).getContent()).isEqualTo(comment2.getContent());
        assertThat(actual.get(1).getCommentId()).isEqualTo(comment2.getId());

        assertThat(actual.get(2).getNickname()).isEqualTo(member.getNickname());
        assertThat(actual.get(2).getContent()).isEqualTo(comment3.getContent());
        assertThat(actual.get(2).getCommentId()).isEqualTo(comment3.getId());
    }

    @Test
    @DisplayName("답글 목록 조회 시 답글이 없으면 빈 리스트가 반환된다")
    public void findReCommentListEmptyListTest() throws Exception {
        // given
        Long 답글이_존재하지_않는_댓글_ID = 0L;

        // when
        List<GetReplyListResponse> actual = commentRepository.findReplyList(답글이_존재하지_않는_댓글_ID);

        // then
        assertThat(actual.size()).isEqualTo(0);
    }

    private void clearRepository() {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
