package com.graphy.backend.domain.message.repository;

import com.graphy.backend.domain.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
