package com.graphy.backend.domain.message.repository.custom;

import com.graphy.backend.domain.message.dto.response.GetMessageResponse;
import com.graphy.backend.domain.message.dto.response.QGetMessageResponse;
import com.graphy.backend.domain.message.repository.MessageCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.graphy.backend.domain.member.domain.QMember.member;
import static com.graphy.backend.domain.message.domain.QMessage.message;

@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GetMessageResponse> findMessages(Pageable pageable, Long memberId) {
        return jpaQueryFactory
                .select(new QGetMessageResponse(
                        member.id,
                        member.nickname,
                        message.content,
                        message.createdAt
                ))
                .from(message)
                .join(message.sender, member)
                .where(message.receiver.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
