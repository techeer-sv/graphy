package com.graphy.backend.domain.member.entity;

import com.graphy.backend.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id,
                  String email,
                  String password,
                  String nickname,
                  String introduction,
                  Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.role = role;
    }
}
