package com.matheustorres.eadhub.notification.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationCommandDTO(
        @NotNull(message = "O campo user_id é obrigatório!")
        @NotBlank(message = "O campo user_id é obrigatório!")
        String userId,

        @NotNull(message = "O campo title é obrigatório!")
        @NotBlank(message = "O campo title é obrigatório!")
        String title,

        @NotNull(message = "O campo message é obrigatório!")
        @NotBlank(message = "O campo message é obrigatório!")
        String message) {
}
