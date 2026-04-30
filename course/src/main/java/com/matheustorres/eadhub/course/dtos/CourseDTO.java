package com.matheustorres.eadhub.course.dtos;

import java.util.UUID;

import com.matheustorres.eadhub.course.domain.enums.CourseLevel;
import com.matheustorres.eadhub.course.domain.enums.CourseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @NotBlank String name,

        @NotBlank String description,

        String imageUrl,

        @NotNull CourseStatus courseStatus,

        @NotNull UUID userInstructor,

        @NotNull CourseLevel courseLevel) {

}
