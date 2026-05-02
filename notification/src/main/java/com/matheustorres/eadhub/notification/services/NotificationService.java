package com.matheustorres.eadhub.notification.services;

import com.matheustorres.eadhub.notification.domain.models.Notification;

public interface NotificationService {
    Notification saveNotification(Notification notification);
}
