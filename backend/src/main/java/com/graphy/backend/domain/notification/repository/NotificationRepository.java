package com.graphy.backend.domain.notification.repository;

import com.graphy.backend.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
