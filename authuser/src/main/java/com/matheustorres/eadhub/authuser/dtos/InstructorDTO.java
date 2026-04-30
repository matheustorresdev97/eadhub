package com.matheustorres.eadhub.authuser.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record InstructorDTO(
        @NotNull UUID userId) {

}
