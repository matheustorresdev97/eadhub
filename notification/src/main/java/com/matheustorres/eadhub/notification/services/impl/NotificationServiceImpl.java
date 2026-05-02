package com.matheustorres.eadhub.notification.services.impl;

import org.springframework.stereotype.Service;

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
}
