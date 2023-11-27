package com.graphy.backend.domain.recruitment.repository.custom;

import com.graphy.backend.domain.recruitment.domain.Position;
import com.graphy.backend.domain.recruitment.domain.Recruitment;
import com.graphy.backend.domain.recruitment.repository.RecruitmentCustomRepository;
import com.graphy.backend.global.util.QueryDslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.graphy.backend.domain.member.domain.QMember.member;
import static com.graphy.backend.domain.project.domain.QTag.tag;
import static com.graphy.backend.domain.recruitment.domain.QRecruitment.recruitment;
import static com.graphy.backend.domain.recruitment.domain.QRecruitmentTag.recruitmentTag;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class RecruitmentCustomRepositoryImpl implements RecruitmentCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Recruitment> findRecruitments(List<Position> positions,
                                              List<String> tags,
                                              String keyword,
                                              Boolean isRecruiting,
                                              Pageable pageable) {

        List<OrderSpecifier> orders = QueryDslUtil.getAllOrderSpecifiers(pageable, recruitment.getMetadata().getName());

        return jpaQueryFactory
                .selectFrom(recruitment)
                .distinct()
                .where(
                        tagIn(tags),
                        positionIn(positions),
                        recruitmentKeywordContains(keyword),
                        isRecruiting(isRecruiting)
                )
                .join(recruitment.member, member).fetchJoin()
                .leftJoin(recruitmentTag).on(recruitmentTag.recruitment.eq(recruitment))
                .leftJoin(recruitmentTag.tag, tag)
                .offset(pageable.getOffset())
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize())

                .fetch();
    }

    @Override
    public Optional<Recruitment> findRecruitmentWithMember(Long recruitmentId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(recruitment)
                .where(recruitment.id.eq(recruitmentId))
                .join(recruitment.member, member).fetchJoin()
                .fetchOne());
    }

    private BooleanExpression tagIn(List<String> tags) {
        if (tags == null || tags.isEmpty()) return null;
        return tag.tech.in(tags);
    }

    private BooleanExpression positionIn(List<Position> positions) {
        if (positions == null || positions.isEmpty()) return null;
        return recruitment.position.in(positions);
    }

    private BooleanBuilder recruitmentKeywordContains(String keyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(keyword)) {
            booleanBuilder.or(recruitment.title.contains(keyword));
            booleanBuilder.or(recruitment.content.contains(keyword));
        }
        return booleanBuilder;
    }

    private BooleanExpression isRecruiting(Boolean isRecruiting) {
        if(isRecruiting == null) return null;
        return recruitment.isRecruiting.eq(isRecruiting);
    }
}

