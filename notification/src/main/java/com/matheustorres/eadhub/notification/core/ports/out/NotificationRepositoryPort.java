package com.matheustorres.eadhub.notification.core.ports.out;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matheustorres.eadhub.notification.core.domain.Notification;
import com.matheustorres.eadhub.notification.core.domain.NotificationStatus;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);

    Page<Notification> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus,
            Pageable pageable);

    Optional<Notification> findByIdAndUserId(UUID notificationId, UUID userId);
}
