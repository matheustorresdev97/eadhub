package com.matheustorres.eadhub.notification.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheustorres.eadhub.notification.domain.enums.NotificationStatus;
import com.matheustorres.eadhub.notification.domain.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus,
            Pageable pageable);

    Optional<Notification> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
