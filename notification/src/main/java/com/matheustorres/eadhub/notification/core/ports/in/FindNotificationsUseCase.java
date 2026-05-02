package com.matheustorres.eadhub.notification.core.ports.in;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matheustorres.eadhub.notification.core.domain.Notification;

public interface FindNotificationsUseCase {
    Page<Notification> findAllByUser(UUID userId, Pageable pageable);
    Optional<Notification> findByIdAndUserId(UUID notificationId, UUID userId);
}
