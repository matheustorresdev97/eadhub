package com.matheustorres.eadhub.notification.adapters.out.persistence;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.notification.core.domain.Notification;

@Component
public class NotificationPersistenceMapper {

    public NotificationJpaEntity toEntity(Notification notification) {
        return NotificationJpaEntity.builder()
                .notificationId(notification.getNotificationId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .creationDate(notification.getCreationDate())
                .notificationStatus(notification.getNotificationStatus())
                .build();
    }

    public Notification toDomain(NotificationJpaEntity entity) {
        return Notification.builder()
                .notificationId(entity.getNotificationId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .creationDate(entity.getCreationDate())
                .notificationStatus(entity.getNotificationStatus())
                .build();
    }
}
