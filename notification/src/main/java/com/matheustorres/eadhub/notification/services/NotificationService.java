package com.matheustorres.eadhub.notification.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matheustorres.eadhub.notification.domain.models.Notification;

public interface NotificationService {
    Notification saveNotification(Notification notification);

    Page<Notification> findAllNotificationByUser(UUID userId, Pageable pageable);

    Optional<Notification> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
