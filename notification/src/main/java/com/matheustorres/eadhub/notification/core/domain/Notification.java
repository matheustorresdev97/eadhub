package com.matheustorres.eadhub.notification.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Notification extends RepresentationModel<Notification> {

    private UUID notificationId;
    private UUID userId;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private NotificationStatus notificationStatus;

}
