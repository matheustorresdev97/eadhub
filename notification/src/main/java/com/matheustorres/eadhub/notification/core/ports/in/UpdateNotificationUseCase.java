package com.matheustorres.eadhub.notification.core.ports.in;

import com.matheustorres.eadhub.notification.core.domain.Notification;
import com.matheustorres.eadhub.notification.core.domain.NotificationStatus;

import java.util.UUID;

public interface UpdateNotificationUseCase {
    Notification updateStatus(UUID notificationId, UUID userId, NotificationStatus status);
}
