package com.graphy.backend.domain.notification.repository;

import com.graphy.backend.domain.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByMemberId(Long memberId, Pageable pageable);
}
