package com.graphy.backend.domain.member.domain;

import com.graphy.backend.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    private String introduction;

    @Column(nullable = true)
    @ColumnDefault("0")
    private int followerCount;
    @Column(nullable = true)
    @ColumnDefault("0")
    private int followingCount;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id,
                  String email,
                  String password,
                  String nickname,
                  String introduction,
                  int followerCount,
                  int followingCount,
                  Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.role = role;
    }
}
