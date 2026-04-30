package com.matheustorres.eadhub.course.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CourseUserDTO(@NotNull UUID courseId,
        @NotNull UUID userId) {

}
