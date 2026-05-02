package com.matheustorres.eadhub.notification.mappers;

import com.matheustorres.eadhub.notification.domain.enums.NotificationStatus;
import com.matheustorres.eadhub.notification.domain.models.Notification;
import com.matheustorres.eadhub.notification.dtos.NotificationCommandDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationCommandDTO notificationCommandDTO) {
        return Notification.builder()
                .userId(UUID.fromString(notificationCommandDTO.userId()))
                .title(notificationCommandDTO.title())
                .message(notificationCommandDTO.message())
                .notificationStatus(NotificationStatus.CREATED)
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }
}
