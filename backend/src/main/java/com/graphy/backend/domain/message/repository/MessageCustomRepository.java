package com.graphy.backend.domain.message.repository;

import com.graphy.backend.domain.message.dto.response.GetMessageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageCustomRepository {
    List<GetMessageResponse> findMessages(Pageable pageable, Long memberId);

}
