package com.matheustorres.eadhub.notification.adapters.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.notification.core.domain.Notification;
import com.matheustorres.eadhub.notification.core.domain.NotificationStatus;
import com.matheustorres.eadhub.notification.core.ports.out.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationPersistenceMapper mapper;

    @Override
    public Notification save(Notification notification) {
        var entity = mapper.toEntity(notification);
        var savedEntity = notificationJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Page<Notification> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus,
            Pageable pageable) {
        var entitiesPage = notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, notificationStatus, pageable);
        return entitiesPage.map(mapper::toDomain);
    }

    @Override
    public Optional<Notification> findByIdAndUserId(UUID notificationId, UUID userId) {
        var entityOptional = notificationJpaRepository.findByNotificationIdAndUserId(notificationId, userId);
        return entityOptional.map(mapper::toDomain);
    }
}
