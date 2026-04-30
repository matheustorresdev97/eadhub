package com.matheustorres.eadhub.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record LessonDTO(
        @NotBlank String name,

        String description,

        @NotBlank String videoUrl) {

}
