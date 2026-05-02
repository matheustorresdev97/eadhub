package com.matheustorres.eadhub.notification.adapters.in.web.dto;

import com.matheustorres.eadhub.notification.core.domain.NotificationStatus;

import jakarta.validation.constraints.NotNull;

public record NotificationStatusUpdateDTO(
        @NotNull NotificationStatus notificationStatus) {

}
