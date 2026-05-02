package com.matheustorres.eadhub.notification.core.usecases;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matheustorres.eadhub.notification.core.domain.Notification;
import com.matheustorres.eadhub.notification.core.domain.NotificationStatus;
import com.matheustorres.eadhub.notification.core.ports.in.CreateNotificationUseCase;
import com.matheustorres.eadhub.notification.core.ports.in.FindNotificationsUseCase;
import com.matheustorres.eadhub.notification.core.ports.in.UpdateNotificationUseCase;
import com.matheustorres.eadhub.notification.core.ports.out.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationUseCaseImpl implements FindNotificationsUseCase, UpdateNotificationUseCase, CreateNotificationUseCase {

    private final NotificationRepositoryPort notificationRepositoryPort;

    @Override
    public Notification create(String userId, String title, String message) {
        Notification notification = Notification.builder()
                .userId(UUID.fromString(userId))
                .title(title)
                .message(message)
                .notificationStatus(NotificationStatus.CREATED)
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
        return notificationRepositoryPort.save(notification);
    }

    @Override
    public Page<Notification> findAllByUser(UUID userId, Pageable pageable) {
        return notificationRepositoryPort.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
    }

    @Override
    public Optional<Notification> findByIdAndUserId(UUID notificationId, UUID userId) {
        return notificationRepositoryPort.findByIdAndUserId(notificationId, userId);
    }

    @Override
    public Notification updateStatus(UUID notificationId, UUID userId, NotificationStatus status) {
        Optional<Notification> notificationOptional = notificationRepositoryPort.findByIdAndUserId(notificationId, userId);
        if (notificationOptional.isEmpty()) {
            throw new RuntimeException("Notification not found!");
        }
        Notification notification = notificationOptional.get();
        notification.setNotificationStatus(status);
        return notificationRepositoryPort.save(notification);
    }
}
