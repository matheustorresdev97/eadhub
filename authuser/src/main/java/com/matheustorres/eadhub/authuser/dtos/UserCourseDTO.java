package com.matheustorres.eadhub.authuser.dtos;

import java.util.UUID;

public record UserCourseDTO(
        UUID userId,
        UUID courseId) {

}
