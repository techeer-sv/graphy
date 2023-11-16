package com.graphy.backend.domain.recruitment.domain;

import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.domain.project.domain.Tags;
import com.graphy.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import com.graphy.backend.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 조회수 속성 추가
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer recruitmentCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessType type;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private LocalDateTime period;

    @Builder.Default
    @Column(nullable = false)
    private boolean isRecruiting = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private RecruitmentTags recruitmentTags;

    public void addTag(Tags tags) {
        recruitmentTags.add(this, tags);
    }

    public List<String> getTagNames() {
        return this.recruitmentTags.getTagNames();
    }

    public List<Long> getTagIds() {
        return this.recruitmentTags.getTagIds();
    }

    public void updateRecruitment(UpdateRecruitmentRequest request, Tags tags) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.recruitmentCount = request.getRecruitmentCount();
        this.type = request.getType();
        this.endDate = request.getEndDate();
        this.position = request.getPosition();
        recruitmentTags.clear();
        addTag(tags);
    }

//    public boolean IsRecruiting() {
//        return LocalDateTime.now().isBefore(this.endDate);
//    }
}
