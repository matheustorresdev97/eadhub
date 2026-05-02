package com.matheustorres.eadhub.notification.core.ports.in;

import com.matheustorres.eadhub.notification.core.domain.Notification;

public interface CreateNotificationUseCase {
    Notification create(String userId, String title, String message);
}
