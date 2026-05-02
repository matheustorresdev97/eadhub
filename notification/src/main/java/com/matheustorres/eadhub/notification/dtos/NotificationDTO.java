package com.matheustorres.eadhub.notification.dtos;

import com.matheustorres.eadhub.notification.domain.enums.NotificationStatus;

import jakarta.validation.constraints.NotNull;

public record NotificationDTO(
        @NotNull NotificationStatus notificationStatus) {

}
