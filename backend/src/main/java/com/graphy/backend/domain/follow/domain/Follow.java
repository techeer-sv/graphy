package com.graphy.backend.domain.follow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(name = "following_unique",
                columnNames = {"from_id", "to_id"})
)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_id")
    private Long fromId; // 팔로우를 요청한 사용자

    @Column(name = "to_id")
    private Long toId; // 팔로우 요청을 받은 사용자

    @Builder
    public Follow(Long id,Long fromId, Long toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
    }
}
