package com.matheustorres.eadhub.notification.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.notification.domain.enums.NotificationStatus;
import com.matheustorres.eadhub.notification.domain.models.Notification;
import com.matheustorres.eadhub.notification.repositories.NotificationRepository;
import com.matheustorres.eadhub.notification.services.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> findAllNotificationByUser(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED,
                pageable);

    }

    @Override
    public Optional<Notification> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationRepository.findByNotificationIdAndUserId(notificationId, userId);
    }
}
