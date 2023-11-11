package com.graphy.backend.domain.member.repository;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.member.domain.Role;
import com.graphy.backend.global.config.JpaAuditingConfig;
import com.graphy.backend.global.config.QueryDslConfig;
import com.graphy.backend.test.config.TestProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({
        JpaAuditingConfig.class,
        QueryDslConfig.class
})
@ActiveProfiles(TestProfile.UNIT)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private Member member;
    private Member 팔로우_팔로워가_0인_사용자;

    @BeforeEach
    public void setup() {
        member = memberRepository.save(
                Member.builder()
                        .email("graphy@gmail.com")
                        .role(Role.ROLE_USER)
                        .followerCount(10)
                        .followingCount(11)
                        .introduction("introduce")
                        .password("password")
                        .nickname("name")
                        .role(Role.ROLE_USER)
                        .build()
        );

        팔로우_팔로워가_0인_사용자 = memberRepository.save(
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
    }
    
    @Test
    @DisplayName("팔로워를 1 증가시킨다")
    void increaseFollowerCountTest() throws Exception {
        // given
        int 기존_팔로워_수 = member.getFollowerCount();

        // when
        memberRepository.increaseFollowerCount(member.getId());
        entityManager.flush();
        entityManager.refresh(member);

        // then
        assertThat(member.getFollowerCount()).isEqualTo(기존_팔로워_수 + 1);
    }

    @Test
    @DisplayName("팔로워를 1 감소시킨다")
    void decreaseFollowerCountTest() throws Exception {
        // given
        int 기존_팔로워_수 = member.getFollowerCount();

        // when
        memberRepository.decreaseFollowerCount(member.getId());
        entityManager.flush();
        entityManager.refresh(member);

        // then
        assertThat(member.getFollowerCount()).isEqualTo(기존_팔로워_수 - 1);
    }

    @Test
    @DisplayName("팔로워가 0인 경우 감소시키지 않는다")
    void decreaseFollowerCountZeroFollowerTest() throws Exception {
        // given
        int 기존_팔로워_수 = 팔로우_팔로워가_0인_사용자.getFollowerCount();

        // when
        memberRepository.decreaseFollowerCount(팔로우_팔로워가_0인_사용자.getId());
        entityManager.flush();
        entityManager.refresh(팔로우_팔로워가_0인_사용자);

        // then
        assertThat(팔로우_팔로워가_0인_사용자.getFollowerCount()).isZero();
        assertThat(팔로우_팔로워가_0인_사용자.getFollowerCount()).isNotEqualTo(- 1);
    }

    @Test
    @DisplayName("팔로잉을 1 증가시킨다")
    void increaseFollowingCountTest() throws Exception {
        // given
        int 기존_팔로잉_수 = member.getFollowingCount();

        // when
        memberRepository.increaseFollowingCount(member.getId());
        entityManager.flush();
        entityManager.refresh(member);

        // then
        assertThat(member.getFollowingCount()).isEqualTo(기존_팔로잉_수 + 1);
    }

    @Test
    @DisplayName("팔로잉을 1 감소시킨다")
    void decreaseFollowingCountTest() throws Exception {
        // given
        int 기존_팔로잉_수 = member.getFollowingCount();

        // when
        memberRepository.decreaseFollowingCount(member.getId());
        entityManager.flush();
        entityManager.refresh(member);

        // then
        assertThat(member.getFollowingCount()).isEqualTo(기존_팔로잉_수 - 1);
    }

    @Test
    @DisplayName("팔로잉이 0인 경우 감소시키지 않는다")
    void decreaseFollowingCountZeroFollowingTest() throws Exception {

        // when
        memberRepository.decreaseFollowingCount(팔로우_팔로워가_0인_사용자.getId());
        entityManager.flush();
        entityManager.refresh(팔로우_팔로워가_0인_사용자);

        // then
        assertThat(팔로우_팔로워가_0인_사용자.getFollowingCount()).isZero();
        assertThat(팔로우_팔로워가_0인_사용자.getFollowingCount()).isNotEqualTo(-1);
    }
}
