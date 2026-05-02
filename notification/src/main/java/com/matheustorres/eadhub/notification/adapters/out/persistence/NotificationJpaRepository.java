package com.matheustorres.eadhub.notification.adapters.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheustorres.eadhub.notification.core.domain.NotificationStatus;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, UUID> {
    Page<NotificationJpaEntity> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus,
            Pageable pageable);

    Optional<NotificationJpaEntity> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
