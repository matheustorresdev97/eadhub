package com.matheustorres.eadhub.course.dtos;

import java.util.UUID;

public record NotificationCommandDTO(
        UUID userId,
        String title,
        String message) {

}
